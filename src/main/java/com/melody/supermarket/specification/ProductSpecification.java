package com.melody.supermarket.specification;

import com.melody.supermarket.pojo.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public interface ProductSpecification {
    static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    static Specification<Product> categoryNameLike(String categoryName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("category").get("name"), "%" + categoryName + "%");
    }

    static Specification<Product> yieldDateBetween(Date start, Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("yieldDate"), start, end);
    }
}
