package com.melody.supermarket.controller;

import com.melody.supermarket.dto.SaleDto;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.SaleServices;
import com.melody.supermarket.util.PageRequestUtil;
import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Resource
    private SaleServices saleServices;

    /***
     * 查询所有销售记录
     * @param page 页码
     * @param limit 每页数量
     * @param sortColumn 排序字段
     * @param sort 排序方式
     * @param pid 商品id
     * @param saleCount 销售数量
     * @param categoryName 分类名称
     * @param startCreateDate 开始创建日期
     * @param endCreateDate 结束创建日期
     * @return 销售记录列表
     */
    @GetMapping
    public ResponseEntity<ResponseBody<?>> findSale(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer limit,
                                                    @RequestParam(required = false) String[] sortColumn,
                                                    @RequestParam(required = false) String sort,
                                                    @RequestParam(required = false) Long pid,
                                                    @RequestParam(required = false) Integer saleCount,
                                                    @RequestParam(required = false) String categoryName,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startCreateDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endCreateDate) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, limit, sortColumn, sort);
        SaleDto saleDto = SaleDto.builder().pid(pid).saleCount(saleCount).categoryName(categoryName).startCreateDate(startCreateDate).endCreateDate(endCreateDate).build();
//        如果传了条件
//        if(Objects.isNull(saleDto)) return ResponseEntity.ok(new ResponseBody<>(saleServices.findAll(pageRequest)));
//        else
            return ResponseEntity.ok(new ResponseBody<>(saleServices.findAll(saleDto, pageRequest)));
    }

    /***
     * 添加销售记录
     * @param sale 销售记录
     * @return 添加的销售记录
     */
    @PostMapping
    public ResponseEntity<ResponseBody<?>> insertSale(@RequestBody SaleDto sale) {
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,saleServices.insert(sale)));
    }
}
