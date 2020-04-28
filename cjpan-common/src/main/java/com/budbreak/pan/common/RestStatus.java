package com.budbreak.pan.common;

/**
* @Description: TODO
* @Author:
* @Createed Date: 2018/6/13-下午7:29
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/
public interface RestStatus {
    /**
    * 状态码
    * @return
    */
    int code();

    /**
    * 状态描述
    * @return
    */
    String message();

    /**
    * 设置异常描述
    * @param msg
    */
    void setMessage(String msg);
}