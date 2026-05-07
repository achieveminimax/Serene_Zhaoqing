package com.zqtravel.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Integer page;
    private Integer size;
    private Long total;
    private List<T> items;

    public static <T> PageResult<T> of(Integer page, Integer size, Long total, List<T> items) {
        return PageResult.<T>builder()
                .page(page)
                .size(size)
                .total(total)
                .items(items)
                .build();
    }
}