package com.melody.supermarket.services.impl;

import cn.hutool.core.date.DateTime;
import com.melody.supermarket.dto.SaleDto;
import com.melody.supermarket.dto.SaleProductDto;
import com.melody.supermarket.dto.SaleProductInsertDto;
import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.pojo.Sale;
import com.melody.supermarket.pojo.SaleProduct;
import com.melody.supermarket.repository.ProductRepository;
import com.melody.supermarket.repository.SaleProductRepository;
import com.melody.supermarket.repository.SaleRepository;
import com.melody.supermarket.services.SaleServices;
import com.melody.supermarket.specification.SaleSpecification;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("saleServices")
public class SaleServicesImpl implements SaleServices {
    @Resource
    private SaleRepository saleRepository;

    @Resource
    private ProductRepository productRepository;

    @Resource
    private SaleProductRepository saleProductRepository;

    /***
     * 根据条件查询销售记录后分页并排序
     * @param saleDto 查询条件
     * @param pageable 分页和排序
     * @return 销售记录分页
     */
    @Override
    public Page<Sale> findAll(SaleDto saleDto, Pageable pageable) {
        List<Specification<Sale>> specifications = new ArrayList<>();
//        如果查询条件带有categoryName
        if(StringUtils.isNotBlank(saleDto.getCategoryName())) {
            specifications.add(SaleSpecification.categoryNameLike(saleDto.getCategoryName()));
        }
//        如果查询条件带有startCreateDate和endCreateDate
        if(Objects.nonNull(saleDto.getStartCreateDate())&&Objects.nonNull(saleDto.getEndCreateDate())) {
            specifications.add(SaleSpecification.createDateBetween(saleDto.getStartCreateDate(),saleDto.getEndCreateDate()));
//            如果查询条件只带有endCreateDate
        } else if(Objects.nonNull(saleDto.getEndCreateDate())) {
            specifications.add(SaleSpecification.createDateBefore(saleDto.getEndCreateDate()));
//            如果查询条件只带有startCreateDate
        } else if(Objects.nonNull(saleDto.getStartCreateDate())) {
            specifications.add(SaleSpecification.createDateAfter(saleDto.getStartCreateDate()));
        }
//        将list里面的Specification条件组合
        Specification<Sale> specification = specifications.stream().reduce(Specification::and).orElse(null);
        return saleRepository.findAll(specification,pageable);
    }

    /***
     * 分页查询并排序
     * @param pageable 分页和排序
     * @return 销售记录分页
     */
    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale findById(Long id) {
        Optional<Sale> optional = saleRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else throw new RuntimeException("销售记录不存在");
    }

    @Override
    public Sale insert(SaleProductInsertDto saleProductInsertDto) {
//        传入pid后根据pid查询商品,如果为空则抛出异常,否则获取传入的saleCount和查询到的商品,创建Sale对象并保存
        //将saleProductInsertDto的ids里的所有id查询出来
        List<Product> all = productRepository.findAllById(saleProductInsertDto.getIds().stream().map(SaleProductDto::getId).toList());
        if(all.size() > 0) {
            List<SaleProduct> saleProducts = new ArrayList<>();
            saleProductInsertDto.getIds().forEach(saleProductDto -> saleProducts.add(SaleProduct.builder().count(saleProductDto.getCount()).build()));
            Sale sale = Sale.builder().products(all).createDate(DateTime.now()).saleProducts(saleProducts).build();
            return saleRepository.save(sale);
        } else throw new RuntimeException("商品不存在");
    }


    @Override
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }
}
