package com.budbreak.pan.common;

/**
* @Description: 状态码封装
* @Author:
* @Createed Date: 2018/6/13-下午7:28
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public enum StatusCode implements RestStatus {
    // 请求成功
    OK(200, "请求成功"),
    OK_NO_DATA(201, "请求数据为空"),
    INVALID_REQUEST(400, "参数有误!"),
    UNAUTHORIZED(401, "授权失败!"),
    INVALID_AUTH(403, "无权访问!"),
    SERVER_INTERNAL_ERROR(500, "服务端异常, 请稍后再试");

    private int code;
    private String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public void setMessage(String msg){
        this.message = msg;
        this.code = code;
    }
}
