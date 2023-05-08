package com.melody.supermarket.services.impl;

import com.melody.supermarket.pojo.Category;
import com.melody.supermarket.repository.CategoryRepository;
import com.melody.supermarket.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryServices")
public class CategoryServicesImpl implements CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category insert(Category c) {
        return categoryRepository.save(c);
    }

    @Override
    public Category update(Category c) {
        return categoryRepository.save(c);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
