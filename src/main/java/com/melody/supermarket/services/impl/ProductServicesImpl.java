package com.melody.supermarket.services.impl;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.exception.ParameterException;
import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.repository.ProductRepository;
import com.melody.supermarket.services.ProductServices;
import com.melody.supermarket.specification.ProductSpecification;
import com.melody.supermarket.util.BeanUtil;
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

@Service("productServices")
public class ProductServicesImpl implements ProductServices {
    @Resource
    private ProductRepository productRepository;

    /***
     * 根据条件查询商品后进行分页并排序
     * @param productDto 查询条件
     * @param pageable 分页与排序对象
     * @return 分页后的商品数据
     */
    @Override
    public Page<Product> findAll(ProductDto productDto, Pageable pageable) {
        List<Specification<Product>> specifications = new ArrayList<>();
//        根据传过来的条件添加Specification
//        如果查询条件带有name
        if(StringUtils.isNotBlank(productDto.getName()))
            specifications.add(ProductSpecification.nameLike(productDto.getName()));
//        如果查询条件带有categoryName
        if(StringUtils.isNotBlank(productDto.getCategoryName()))
            specifications.add(ProductSpecification.categoryNameLike(productDto.getCategoryName()));
//        如果查询条件带有startYieldDate和endYieldDate
        if(Objects.nonNull(productDto.getStartYieldDate())&&Objects.nonNull(productDto.getEndYieldDate())) {
            specifications.add(ProductSpecification.yieldDateBetween(productDto.getStartYieldDate(), productDto.getEndYieldDate()));
//        如果查询条件只带有startYieldDate
        }else if(Objects.nonNull(productDto.getStartYieldDate())) {
            specifications.add(ProductSpecification.yieldDateAfter(productDto.getStartYieldDate()));
//        如果查询条件只带有endYieldDate
        } else if(Objects.nonNull(productDto.getEndYieldDate())) {
            specifications.add(ProductSpecification.yieldDateBefore(productDto.getEndYieldDate()));
        }
//        将list里面的Specification条件组合
        Specification<Product> specification = specifications.stream().reduce(Specification::and).orElse(null);
        return productRepository.findAll(specification,pageable);
    }

    /***
     * 分页查询并排序
     * @param pageable 分页与排序对象
     * @return 分页后的商品数据
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Long> findAllIds() {
        return  productRepository.findAllIds();
    }

    @Override
    public List<String> findAllNames() {
        return productRepository.findAllNames();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()) return optional.get();
        else throw new ParameterException("该商品不存在");
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product insert(Product p) {
        return productRepository.save(p);
    }

    @Override
    public Product update(Product p) {
        Optional<Product> optional = productRepository.findById(p.getId());
        if(optional.isPresent()) {
            Product product = optional.get();
            BeanUtil.copyNonNullProperties(p,product);
            return productRepository.save(product);
        } else throw new RuntimeException("该商品不存在");
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
