package com.melody.supermarket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleDto {
    private Long pid;
    private Integer saleCount;
    private String categoryName;
    private Date startCreateDate;
    private Date endCreateDate;
}
