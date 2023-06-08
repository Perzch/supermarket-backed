package com.melody.supermarket.services;

import com.melody.supermarket.dto.SaleDto;
import com.melody.supermarket.dto.SaleProductInsertDto;
import com.melody.supermarket.pojo.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleServices {
    Page<Sale> findAll(SaleDto saleDto, Pageable pageable);

    Page<Sale> findAll(Pageable pageable);

    List<Sale> findAll();

    Sale findById(Long id);

    Sale insert(SaleProductInsertDto saleProductInsertDto);

    void delete(Long id);
}
