package com.budbreak.pan.common;

/**
* @Description: TODO
* @Author:
* @Createed Date: 2018/6/13-下午7:29
* @ModificationHistory: Who  When  What
* ---------     -------------   --------------------------------------
**/

public final class ShiftExp {
    private ShiftExp() {
    }

    public static void fatal(RestStatus status) {
        throw new BizException(status);
    }
}