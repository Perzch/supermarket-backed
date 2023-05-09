package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long>, JpaSpecificationExecutor<Sale> {
}
