package com.example.mp.common;

import lombok.Data;

/**
 * Author: Zhi Liu
 * Date: 2024/5/30 16:02
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class Pagination {
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public Pagination(int currentPage, int totalPages, long totalItems) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}
