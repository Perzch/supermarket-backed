package com.melody.supermarket.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.List;

@Data
@Entity
@Table
@DynamicUpdate
@JsonIgnoreProperties("products")
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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