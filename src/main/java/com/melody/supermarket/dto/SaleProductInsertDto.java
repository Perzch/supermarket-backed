package com.melody.supermarket.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class SaleProductInsertDto {
    List<SaleProductDto> ids;
    Date createDate;
}
