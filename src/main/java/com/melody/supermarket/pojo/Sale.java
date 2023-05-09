package com.melody.supermarket.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class Sale{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date createDate;
    @Column
    private Integer saleCount;
    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;
}

