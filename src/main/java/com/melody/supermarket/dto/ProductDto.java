package com.melody.supermarket.dto;

import com.melody.supermarket.pojo.Product;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto extends Product {
    private Date startYieldDate;
    private Date endYieldDate;
}
