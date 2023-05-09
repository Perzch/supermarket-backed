package com.melody.supermarket.util;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageRequestUtil {
    public static PageRequest getPageRequest(Integer page, Integer limit, String[] sortColumn, String sort) {
        if(Objects.isNull(page)) page =0;
        if(Objects.isNull(limit)) limit = 10;
        PageRequest pageRequest;
        if(StringUtils.isBlank(sort)&&Objects.isNull(sortColumn)) {//没有传排序列与排序方向
            pageRequest = PageRequest.of(page, limit);
        } else if(StringUtils.isBlank(sort)&&Objects.nonNull(sortColumn)) { //没有传排序方向,但是传了排序列,默认排序方向为asc
            pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, sortColumn));
        } else if(StringUtils.isNotBlank(sort)&&Objects.isNull(sortColumn)) { //没有传排序列,但是传了排序方向,默认排序列为id
            pageRequest = PageRequest.of(page, limit,Sort.by(Sort.Direction.fromString(sort), "id"));
        } else { //传了排序列与排序方向
            pageRequest = PageRequest.of(page, limit,Sort.by(Sort.Direction.fromString(sort), sortColumn));
        }
        return pageRequest;
    }
}
