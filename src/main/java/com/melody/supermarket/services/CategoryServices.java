package com.melody.supermarket.services;

import com.melody.supermarket.pojo.Category;

import java.util.List;

public interface CategoryServices {
    Category insert(Category c);

    Category update(Category c);

    void delete(Long id);

    List<Category> findAll();

    Category findByName(String name);
}
