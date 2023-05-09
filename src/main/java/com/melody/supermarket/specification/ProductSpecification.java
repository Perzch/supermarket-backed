package com.melody.supermarket.specification;

import com.melody.supermarket.pojo.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public interface ProductSpecification {
//    模糊查询,root.get("name")获取Product.getName()的值
    static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

//    模糊查询,root.get("category").get("name")获取Product.getCategory().getName()的值
    static Specification<Product> categoryNameLike(String categoryName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("categoryName"), "%" + categoryName + "%");
    }

    static Specification<Product> yieldDateBetween(Date start, Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("yieldDate"), start, end);
    }
}
