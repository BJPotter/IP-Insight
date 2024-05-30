package com.example.mp.common;

import lombok.Data;

/**
 * Author: Zhi Liu
 * Date: 2024/5/30 16:01
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private String error;
    private Pagination pagination;

    public ApiResponse(int status, String message, T data, String error, Pagination pagination) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
        this.pagination = pagination;
    }
}


