package com.melody.supermarket.controller;

import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.repository.ProductRepository;
import com.melody.supermarket.request.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{page}/{limit}")
    public ResponseEntity<ResponseBody<?>> query(@PathVariable Integer page,@PathVariable Integer limit) {
        if(limit == 0) limit = 10;
        Page<Product> all = productRepository.findAll(PageRequest.of(page, limit));
        return ResponseEntity.ok(new ResponseBody<>(all));
    }
}
