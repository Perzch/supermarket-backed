package com.melody.supermarket.services.impl;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.repository.ProductRepository;
import com.melody.supermarket.services.ProductServices;
import com.melody.supermarket.specification.ProductSpecification;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.List;

public class ProductServicesImpl implements ProductServices {
    @Resource
    private ProductRepository productRepository;
    @Override
    public Page<Product> getAll(ProductDto productDto, Pageable pageable) {
//        使用Specification进行一段时间的查询
        ProductSpecification productSpecification;
        return productRepository.findAll(ProductSpecification.categoryNameLike(productDto.getCategoryName())
                .and(ProductSpecification.nameLike(productDto.getName()))
                .and(ProductSpecification.yieldDateBetween(
                        productDto.getStartYieldDate(),
                        productDto.getEndYieldDate())
                ),pageable);
    }

    @Override
    public Product insert(Product p) {
        return null;
    }

    @Override
    public Product update(Product p) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
