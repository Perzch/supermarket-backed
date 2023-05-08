package com.melody.supermarket.services.impl;

import com.melody.supermarket.pojo.User;
import com.melody.supermarket.repository.UserRepository;
import com.melody.supermarket.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServices")
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUsername(User u) {
        return userRepository.findByUsername(u.getUsername());
    }
}
