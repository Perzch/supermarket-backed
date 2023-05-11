package com.melody.supermarket.services;

import com.melody.supermarket.pojo.Category;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryServices {
    Category insert(Category c);

    Category update(Category c);

    void delete(Long id);

    List<Category> findAll();

    List<Category> findAll(Sort sort);
    List<String> findAllNames();

    Category findByName(String name);
}
