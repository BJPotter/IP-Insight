package com.example.mp.util;

import com.example.mp.common.ApiResponse;
import com.example.mp.common.Pagination;

/**
 * Author: Zhi Liu
 * Date: 2024/5/30 16:04
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class ResponseUtil {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data, null, null);
    }

    public static <T> ApiResponse<T> success(T data, Pagination pagination) {
        return new ApiResponse<>(200, "Success", data, null, pagination);
    }

    public static <T> ApiResponse<T> error(int status, String message, String error) {
        return new ApiResponse<>(status, message, null, error, null);
    }
}
