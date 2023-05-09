package com.melody.supermarket.controller;

import com.melody.supermarket.pojo.Category;
import com.melody.supermarket.repository.CategoryRepository;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    private CategoryServices categoryServices;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseBody<Category>> add(@RequestBody Category c) {
//        如果参数有效
        if(StringUtils.hasText(c.getName())&&StringUtils.hasText(c.getRecommend())) {
            Category byName = categoryRepository.findByName(c.getName());
//            如果数据库里有数据
            if(Objects.nonNull(byName)) {
                return ResponseEntity.ok(new ResponseBody<>(500,"该分类已存在",null));
            } else {
                return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,categoryRepository.save(c)));
            }
        } else {
//            参数无效
            return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
        }
    }

    @PutMapping
    public ResponseEntity<ResponseBody<Category>> update(@RequestBody Category c) {
//        如果参数有效
//        TODO:  有bug,无法做到部分字段更新
        if(Objects.isNull(c.getId())) return ResponseEntity.ok(new ResponseBody<>(Code.ID_EMPTY));
        if(Objects.nonNull(c.getProducts())) return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
        return ResponseEntity.ok(
                new ResponseBody<>(
                        Code.UPDATED,
                        categoryRepository.save(c)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<String>> delete(@PathVariable Long id){
        if(Objects.isNull(id)) return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseBody<>(Code.DELETED));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<?>> query() {
        return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryRepository.findAll()));
    }
}
