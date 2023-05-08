package com.melody.supermarket.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User{
    @Id
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Transient
    private String captcha;

}