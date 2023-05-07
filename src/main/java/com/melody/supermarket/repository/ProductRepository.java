package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
