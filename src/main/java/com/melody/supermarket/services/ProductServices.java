package com.melody.supermarket.services;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.pojo.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.support.PageableUtils;

import java.util.List;

public interface ProductServices {
    Page<Product> getAll(ProductDto productDto, Pageable pageable);

    Product insert(Product p);

    Product update(Product p);

    void delete(Long id);
}
