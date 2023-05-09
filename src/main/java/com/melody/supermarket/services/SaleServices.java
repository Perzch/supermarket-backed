package com.melody.supermarket.services;

import com.melody.supermarket.dto.SaleDto;
import com.melody.supermarket.pojo.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleServices {
    Page<Sale> findAll(SaleDto saleDto, Pageable pageable);

    Page<Sale> findAll(Pageable pageable);

    Sale insert(SaleDto s);

    void delete(Long id);
}
