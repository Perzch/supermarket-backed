package com.melody.supermarket.repository;

import com.melody.supermarket.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("select c.name from Category c")
    List<String> findAllNames();
}
