package com.melody.supermarket.controller;

import com.melody.supermarket.pojo.Category;
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
    @Autowired
    private CategoryServices categoryServices;


    /***
     * 添加分类
     * @param c 分类对象
     * @return 添加成功的分类对象
     */
    @PostMapping
    public ResponseEntity<ResponseBody<Category>> addCategory(@RequestBody Category c) {
        if(StringUtils.isBlank(c.getName())||StringUtils.isBlank(c.getRecommend())||Objects.nonNull(c.getId())) {
            return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
        }
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,categoryServices.insert(c)));
    }

    /***
     * 更新分类
     * @param c 分类对象
     * @return 更新成功的分类对象
     */
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

    /***
     * 删除分类
     * @param id 分类id
     * @return 删除成功的响应
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<String>> deleteCategory(@PathVariable Long id){
        categoryServices.delete(id);
        return ResponseEntity.ok(new ResponseBody<>(Code.DELETED));
    }

    /***
     *
     * @param sortColumn 排序字段
     * @param sort 排序方式
     * @return 分类列表
     */
    @GetMapping
    public ResponseEntity<ResponseBody<?>> queryCategory(@RequestParam(required = false) String sortColumn,
                                                         @RequestParam(required = false) String sort) {
        if(StringUtils.isBlank(sortColumn)) return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryServices.findAll()));
        else return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryServices.findAll(Sort.by(Sort.Direction.fromString(sort),sortColumn))));
    }

    /***
     * 查询所有分类名称
     * @return 分类名称列表
     */
    @GetMapping("/names")
    public ResponseEntity<ResponseBody<?>> queryCategoryNames() {
        return ResponseEntity.ok(new ResponseBody<>(Code.QUERY_SUCCESS,categoryServices.findAllNames()));
    }
}
