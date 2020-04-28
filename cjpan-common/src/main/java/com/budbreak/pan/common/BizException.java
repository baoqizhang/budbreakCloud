package com.budbreak.pan.common;

/**
* @Description: 业务异常类
* @Author: kissy
* @Createed Date: 2018/6/13-下午7:28
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -2359843215972162510L;
    private RestStatus restStatus;

    public BizException() {
    }

    public BizException(RestStatus status) {
        super(status.message());
        this.restStatus = status;
    }

    public BizException(String message){
        super(message);
        this.restStatus = StatusCode.SERVER_INTERNAL_ERROR;
        this.restStatus.setMessage(message);
    }

    public BizException(StatusCode code, String message) {
        super(message);
        this.restStatus = code;
        this.restStatus.setMessage(message);
    }

    public RestStatus getRestStatus() {
        return this.restStatus;
    }
}
