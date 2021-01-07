package com.ifoxhub.admin.common.exception;


import com.ifoxhub.admin.common.api.IErrorCode;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/1/7
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
