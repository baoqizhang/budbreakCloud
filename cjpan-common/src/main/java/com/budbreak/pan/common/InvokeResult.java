package com.budbreak.pan.common;

import java.util.ArrayList;

/**
* @Description: REST调用返回类
* @Author:
* @Createed Date: 2018/6/13-下午7:29
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public class InvokeResult<T> {
    private int code;
    private String msg;
    private T data;

    public static InvokeResult success(){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setMsg("成功!");
        invokeResult.setCode(200);
        invokeResult.setData(new ArrayList<>());
        return invokeResult;
    }

    public static InvokeResult success(Object data){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setData(data);
        invokeResult.setCode(200);
        invokeResult.setMsg("成功!");
        return invokeResult;
    }

    public static InvokeResult failure(String msg){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(400);
        invokeResult.setMsg(msg);
        return invokeResult;
    }

    public static InvokeResult failure(int code, String msg){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(code);
        invokeResult.setMsg(msg);
        return invokeResult;
    }

    public static InvokeResult error(){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(500);
        invokeResult.setMsg("服务内部错误!");
        return invokeResult;
    }

    public static InvokeResult passNoData(){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(201);
        invokeResult.setMsg("查询数据为空!");
        return invokeResult;
    }
    public static InvokeResult unauthorized(){
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(StatusCode.UNAUTHORIZED.code());
        invokeResult.setMsg("授权失败!");
        return invokeResult;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus(){
        return this.code > 1000?400:this.code;
    }
}