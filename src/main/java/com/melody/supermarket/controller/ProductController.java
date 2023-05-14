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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductServices productServices;

    /***
     * 查询所有商品
     * @param page 页码
     * @param limit 每页数量
     * @param sortColumn 排序字段
     * @param sort 排序方式
     * @param name 商品名称
     * @param categoryName 分类名称
     * @param startYieldDate 开始生产日期
     * @param endYieldDate 结束生产日期
     * @return 商品列表
     */

    @GetMapping
    public ResponseEntity<ResponseBody<?>> findProduct(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer limit,
                                                       @RequestParam(required = false) String[] sortColumn,
                                                       @RequestParam(required = false) String sort,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String categoryName,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startYieldDate,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endYieldDate) {
//        构建分页对象
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, limit, sortColumn, sort);
//        构建查询条件
        ProductDto productDto = ProductDto.builder().startYieldDate(startYieldDate).endYieldDate(endYieldDate).build();
        productDto.setName(name);
        productDto.setCategoryName(categoryName);
        return ResponseEntity.ok(new ResponseBody<>(productServices.findAll(productDto, pageRequest)));
    }

    /***
     * 根据商品名称查询商品
     * @param name 商品名称
     * @return 商品列表
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseBody<?>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(new ResponseBody<>(productServices.findByName(name)));
    }

    /***
     * 根据商品id查询商品
     * @param id 商品id
     * @return 商品
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseBody<>(productServices.findById(id)));
    }

    /***
     * 查询所有商品id
     * @return 商品id列表
     */
    @GetMapping("/ids")
    public ResponseEntity<ResponseBody<?>> findProductIds() {
        return ResponseEntity.ok(new ResponseBody<>(productServices.findAllIds()));
    }

    /***
     * 查询所有商品名称
     * @return 商品名称列表
     */
    @GetMapping("/names")
    public ResponseEntity<ResponseBody<?>> findProductNames() {
        return ResponseEntity.ok(new ResponseBody<>(productServices.findAllNames()));
    }

    /***
     * 新增商品
     * @param product 商品
     * @return 新增的商品
     */
    @PostMapping
    public ResponseEntity<ResponseBody<?>> insertProduct(@RequestBody @Valid Product product) {
        if(Objects.nonNull(product.getId())) throw new ParameterException("新增时不能传id");
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,productServices.insert(product)));
    }

    /***
     * 更新商品
     * @param product 商品
     * @return 更新的商品
     */
    @PutMapping
    public ResponseEntity<ResponseBody<?>> updateProduct(@RequestBody Product product) {
        if(Objects.isNull(product.getId())) throw new ParameterException("更新时必须传id");
        return ResponseEntity.ok(new ResponseBody<>(Code.UPDATED,productServices.update(product)));
    }

    /***
     * 根据id删除商品
     * @param id 商品id
     * @return 删除成功
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> deleteProduct(@PathVariable Long id) {
        productServices.delete(id);
        return ResponseEntity.ok(new ResponseBody<>(Code.DELETED));
    }
}
