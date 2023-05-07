package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
