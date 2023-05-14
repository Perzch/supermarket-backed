package com.melody.supermarket.services;

import com.melody.supermarket.pojo.User;

public interface UserServices {
    User findByUsername(User u);

    String verifyPassword(User u);
}
