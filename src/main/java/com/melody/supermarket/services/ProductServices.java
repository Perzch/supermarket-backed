package com.melody.supermarket.services;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductServices {
    Page<Product> findAll(ProductDto productDto, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    List<Long> findAllIds();

    List<String> findAllNames();

    Product findById(Long id);

    Product findByName(String name);

    Product insert(Product p);

    Product update(Product p);

    void delete(Long id);

}
