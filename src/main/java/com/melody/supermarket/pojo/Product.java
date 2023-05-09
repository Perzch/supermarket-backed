package com.melody.supermarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@JsonIgnoreProperties(value = {"sales","category"})
@NotNull(message = "商品信息不能为空")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank(message = "商品名称不能为空")
    private String name;
    /**
     * 生产日期
     */
    @Column
    @NotNull(message = "生产日期不能为空")
    private Date yieldDate;
    /**
     * 产家
     */
    @Column
    @NotBlank(message = "产家不能为空")
    private String manufacturers;
    @Column
    @NotNull(message = "进价不能为空")
    @DecimalMin(value = "0", message = "进价不能小于0")
    private BigDecimal price;
    /**
     * 进货日期
     */
    @Column
    @NotNull(message = "进货日期不能为空")
    private Date createDate;
    @Column
    @NotNull(message = "库存不能为空")
    @DecimalMin(value = "0", message = "库存不能小于0")
    private Integer stock;
    /**
     * 售价
     */
    @Column
    @NotNull(message = "售价不能为空")
    @DecimalMin(value = "0", message = "售价不能小于0")
    private BigDecimal nowPrice;
    @Column
    @NotNull(message = "销量不能为空")
    @DecimalMin(value = "0", message = "销量不能小于0")
    private Integer saleCount;
    @Column(name = "category_name")
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
    @ManyToOne(targetEntity = Category.class, cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Category category;
    @OneToMany(mappedBy = "product")
    private List<Sale> sales;
}

