package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long>, JpaSpecificationExecutor<Sale> {
    @Query("select s,sp.count from Sale s,SaleProduct sp where s.id=sp.sale.id")
    List<Object[]> findAllSalesWithProductCount();
}
