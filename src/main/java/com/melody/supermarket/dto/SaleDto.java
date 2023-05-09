package com.melody.supermarket.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SaleDto {
    private Long pid;
    private Integer saleCount;
    private String categoryName;
    private Date startCreateDate;
    private Date endCreateDate;
}
