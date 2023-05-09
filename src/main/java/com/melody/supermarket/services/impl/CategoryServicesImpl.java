package com.melody.supermarket.services.impl;

import com.melody.supermarket.exception.ExistException;
import com.melody.supermarket.pojo.Category;
import com.melody.supermarket.repository.CategoryRepository;
import com.melody.supermarket.services.CategoryServices;
import com.melody.supermarket.util.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("categoryServices")
public class CategoryServicesImpl implements CategoryServices {
    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public Category insert(Category c) {
        if(Objects.nonNull(categoryRepository.findByName(c.getName()))) {
            throw new ExistException("该分类已存在");
        }
        return categoryRepository.save(c);
    }

    @Override
    public Category update(Category c) {
        Optional<Category> id = categoryRepository.findById(c.getId());
        if(id.isPresent()) {
            if(Objects.nonNull(categoryRepository.findByName(c.getName()))) {
                throw new RuntimeException("分类名重复");
            }
            Category byId = id.get();
            BeanUtil.copyNonNullProperties(c,byId);
            return categoryRepository.save(byId);
        } else {
            throw new RuntimeException("该分类不存在");
        }
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
