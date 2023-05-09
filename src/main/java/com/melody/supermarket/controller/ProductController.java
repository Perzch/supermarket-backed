package com.melody.supermarket.controller;

import com.melody.supermarket.dto.ProductDto;
import com.melody.supermarket.exception.ParameterException;
import com.melody.supermarket.pojo.Product;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.ProductServices;
import com.melody.supermarket.util.PageRequestUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
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
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, limit, sortColumn, sort);
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
