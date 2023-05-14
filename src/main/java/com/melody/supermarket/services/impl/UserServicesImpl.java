package com.melody.supermarket.services.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.melody.supermarket.exception.ExistException;
import com.melody.supermarket.pojo.User;
import com.melody.supermarket.repository.UserRepository;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.services.UserServices;
import com.melody.supermarket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service("userServices")
public class UserServicesImpl implements UserServices {

    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${rsa.public_key}")
    private String publicKey;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUsername(User u) {
        return userRepository.findByUsername(u.getUsername());
    }

    @Override
    public String verifyPassword(User u) {
        RSA rsa = new RSA(privateKey, publicKey);
        byte[] decrypt = rsa.decrypt(u.getPassword(), KeyType.PrivateKey);
        User user = findByUsername(u);
        if(Objects.isNull(user)) throw new ExistException(Code.USER_NOT_EXIST.getMsg());
        if(user.getPassword().equals(StrUtil.str(decrypt, StandardCharsets.UTF_8))) {
             return JwtUtil.createToken(user.getUsername());
        } else {
            return null;
        }
    }
}
