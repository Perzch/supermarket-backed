package com.melody.supermarket.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

//自定义状态码
@AllArgsConstructor
@Getter
public enum Code {
    SUCCESS(200,"操作成功!"),
    UPDATED(200,"更新成功!"),
    INSERTED(200,"添加成功!"),
    DELETED(200,"删除成功!"),
    QUERY_SUCCESS(200, "查询成功!"),
    PARAMETER(400,"参数错误!"),
    ID_EMPTY(400,"id为空!"),
    WARNING(400,"操作失败!"),
    EMPTY(400,"没有数据需要操作!"),
    INVALID(400,"无效签名!"),
    EXPIRED(400,"token过期!"),
    NULLITY(400,"非法访问!"),
    NODATA(500,"暂无数据!"),
    SERVER(500,"服务器错误!"),
    USER_NOT_FOUND(401,"用户不存在!"),
    PASSWORD_ERROR(401, "密码错误!"),
    CAPTCHA_ERROR(400, "验证码错误!");

    final int code;
    final String msg;

}
