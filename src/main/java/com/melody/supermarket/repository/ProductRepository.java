package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p.id from Product p")
    List<Long> findAllIds();

    @Query("select p.name from Product p")
    List<String> findAllNames();

    Product findByName(String name);
}
