package com.melody.supermarket.controller;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.exception.ParameterException;
import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.ProductServices;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductServices productServices;

    @GetMapping
    public ResponseEntity<ResponseBody<?>> findProduct(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer limit,
                                                       @RequestParam(required = false) String[] sortColumn,
                                                       @RequestParam(required = false) String sort,
                                                       @RequestBody(required = false) ProductDto productDto) {
        if(Objects.isNull(page)) page =0;
        if(Objects.isNull(limit)) limit = 10;
        PageRequest pageRequest;
        if(StringUtils.isBlank(sort)&&Objects.isNull(sortColumn)) {//没有传排序列与排序方向
            pageRequest = PageRequest.of(page, limit);
        } else if(StringUtils.isBlank(sort)&&Objects.nonNull(sortColumn)) { //没有传排序方向,但是传了排序列,默认排序方向为asc
            pageRequest = PageRequest.of(page, limit,Sort.by(Sort.Direction.ASC, sortColumn));
        } else if(StringUtils.isNotBlank(sort)&&Objects.isNull(sortColumn)) { //没有传排序列,但是传了排序方向,默认排序列为id
            pageRequest = PageRequest.of(page, limit,Sort.by(Sort.Direction.fromString(sort), "id"));
        } else { //传了排序列与排序方向
            pageRequest = PageRequest.of(page, limit,Sort.by(Sort.Direction.fromString(sort), sortColumn));
        }
//        如果传了条件
        if(Objects.isNull(productDto)) return ResponseEntity.ok(new ResponseBody<>(productServices.findAll(pageRequest)));
        else return ResponseEntity.ok(new ResponseBody<>(productServices.findAll(productDto, pageRequest)));
    }

    @PostMapping
    public ResponseEntity<ResponseBody<?>> insertProduct(@RequestBody @Valid Product product) {
        if(Objects.nonNull(product.getId())) throw new ParameterException("新增时不能传id");
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,productServices.insert(product)));
    }

    @PutMapping
    public ResponseEntity<ResponseBody<?>> updateProduct(@RequestBody Product product) {
        if(Objects.isNull(product.getId())) throw new ParameterException("更新时必须传id");
        return ResponseEntity.ok(new ResponseBody<>(Code.UPDATED,productServices.update(product)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> deleteProduct(@PathVariable Long id) {
        productServices.delete(id);
        return ResponseEntity.ok(new ResponseBody<>(Code.DELETED));
    }
}
