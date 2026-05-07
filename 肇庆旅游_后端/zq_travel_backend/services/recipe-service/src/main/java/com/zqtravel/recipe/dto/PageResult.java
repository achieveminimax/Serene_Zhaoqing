package com.zqtravel.recipe.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
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

    public static <T> PageResult<T> of(IPage<T> page) {
        return PageResult.<T>builder()
                .page((int) page.getCurrent())
                .size((int) page.getSize())
                .total(page.getTotal())
                .items(page.getRecords())
                .build();
    }
}