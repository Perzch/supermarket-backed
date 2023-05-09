package com.melody.supermarket.dto;

import com.melody.supermarket.pojo.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDto extends Product {
    private Date startYieldDate;
    private Date endYieldDate;
}
