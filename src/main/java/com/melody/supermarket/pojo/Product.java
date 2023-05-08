package com.melody.supermarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@JsonIgnoreProperties(value = {"sales", "category"})
public class Product{
    @Id
    private Long id;
    @Column
    private String name;
    /**
     * 生产日期
     */
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date yieldDate;
    /**
     * 产家
     */
    @Column
    private String manufacturers;
    @Column
    private BigDecimal price;
    /**
     * 进货日期
     */
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createDate;
    @Column
    private Integer stock;
    /**
     * 售价
     */
    @Column
    private BigDecimal nowPrice;
    @Column
    private Integer saleCount;
    @Column(name = "category_name")
    private String categoryName;
    @ManyToOne(targetEntity = Category.class, cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Category category;
    @ManyToMany(mappedBy = "products")
    private List<Sale> sales;
}

