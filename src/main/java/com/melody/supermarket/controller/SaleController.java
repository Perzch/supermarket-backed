package com.melody.supermarket.controller;

import com.melody.supermarket.dto.SaleDto;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.SaleServices;
import com.melody.supermarket.util.PageRequestUtil;
import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Resource
    private SaleServices saleServices;

    @GetMapping
    public ResponseEntity<ResponseBody<?>> findSale(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer limit,
                                                    @RequestParam(required = false) String[] sortColumn,
                                                    @RequestParam(required = false) String sort,
                                                    @RequestBody(required = false) SaleDto saleDto) {
        PageRequest pageRequest = PageRequestUtil.getPageRequest(page, limit, sortColumn, sort);
//        如果传了条件
        if(Objects.isNull(saleDto)) return ResponseEntity.ok(new ResponseBody<>(saleServices.findAll(pageRequest)));
        else return ResponseEntity.ok(new ResponseBody<>(saleServices.findAll(saleDto, pageRequest)));
    }

    @PostMapping
    public ResponseEntity<ResponseBody<?>> insertSale(@RequestBody SaleDto sale) {
        return ResponseEntity.ok(new ResponseBody<>(Code.INSERTED,saleServices.insert(sale)));
    }
}
