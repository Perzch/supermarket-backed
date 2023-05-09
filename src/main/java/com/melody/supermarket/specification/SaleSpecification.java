package com.melody.supermarket.specification;

import com.melody.supermarket.pojo.Sale;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public interface SaleSpecification {
    static Specification<Sale> categoryNameLike(String categoryName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("product").get("categoryName"), "%" + categoryName + "%");
    }

    static Specification<Sale> createDateBetween(Date start,Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createDate"), start, end);
    }

    static Specification<Sale> createDateBefore(Date end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("createDate"), end);
    }

    static Specification<Sale> createDateAfter(Date start) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("createDate"), start);
    }
}
