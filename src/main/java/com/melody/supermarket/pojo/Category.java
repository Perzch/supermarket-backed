package com.melody.supermarket.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Category{
    @Id
    private Long id;
    @Column
    private String name;
    /**
     * 推荐指数
     */
    @Column
    private String recommend;
    @Column
    private Integer products;
}

