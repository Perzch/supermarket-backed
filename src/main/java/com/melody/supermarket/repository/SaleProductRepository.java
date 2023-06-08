package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SaleProductRepository extends JpaRepository<SaleProduct,Long>, JpaSpecificationExecutor<SaleProduct> {
    List<SaleProduct> findSaleProductsBySaleId(Long id);
}
