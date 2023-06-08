package com.melody.supermarket.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class User{
    @Id
    private Long id;
    @Column
    @NotBlank(message = "username不能为空")
    private String username;
    @Column
    @NotBlank(message = "password不能为空")
    private String password;
    @Transient
    @NotBlank(message = "验证码不能为空")
    private String captcha;
    @Transient
    @NotBlank(message = "唯一标识不能为空")
    private String uuid;
}