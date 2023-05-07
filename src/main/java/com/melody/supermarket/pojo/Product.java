package com.melody.supermarket.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Product{
    @Id
    private Long id;
    @Column
    private Long cid;
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
    private Double price;
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
    private Double nowPrice;
    @Column
    private Integer saleCount;
    @Column
    private String category;
}

