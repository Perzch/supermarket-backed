package com.melody.supermarket.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table
public class Sale{
    @Id
    private Long id;
    @Column
    private Long pid;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createDate;
    @Column
    private Integer saleCount;
}

