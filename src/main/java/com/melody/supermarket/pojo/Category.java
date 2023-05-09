package com.melody.supermarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@Entity
@Table
@DynamicUpdate
@JsonIgnoreProperties("products")
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id不能为空")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    /**
     * 推荐指数
     */
    @Column(name = "recommend", nullable = false)
    private String recommend;
    /**
     * 该分类下的商品
     */
    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL})
    private List<Product> products;
}