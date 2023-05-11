package com.melody.supermarket.controller;

import com.melody.supermarket.pojo.Category;
import com.melody.supermarket.repository.CategoryRepository;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.CategoryServices;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseBody<Category>> addCategory(@RequestBody Category c) {
        if(StringUtils.isBlank(c.getName())||StringUtils.isBlank(c.getRecommend())||Objects.nonNull(c.getId())) {
            return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
        }
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,categoryServices.insert(c)));
    }

    @PutMapping
    public ResponseEntity<ResponseBody<Category>> updateCategory(@RequestBody @Valid Category c) {
        if(Objects.isNull(c.getId())) return ResponseEntity.ok(new ResponseBody<>(Code.ID_EMPTY));
//        如果参数有效
        return ResponseEntity.ok(
                new ResponseBody<>(
                        Code.UPDATED,
                        categoryServices.update(c)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<String>> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(new ResponseBody<>(Code.DELETED));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<?>> queryCategory(@RequestParam(required = false) String sortColumn,
                                                         @RequestParam(required = false) String sort) {
        if(StringUtils.isBlank(sortColumn)) return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryRepository.findAll()));
        else return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryRepository.findAll(Sort.by(Sort.Direction.fromString(sort),sortColumn))));
    }
}
