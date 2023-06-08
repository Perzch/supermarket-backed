package com.melody.supermarket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleProductDto {
    private Long id;
    private Integer count;
}
