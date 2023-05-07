package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByUsername(String username);
}
