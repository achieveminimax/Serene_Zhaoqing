package com.zqtravel.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果")
public class PageResult<T> {

    @Schema(description = "当前页码")
    private Integer page;

    @Schema(description = "每页大小")
    private Integer size;

    @Schema(description = "总记录数")
    private Long total;

    @Schema(description = "总页数")
    private Integer pages;

    @Schema(description = "数据列表")
    private List<T> records;

    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(Integer page, Integer size, Long total, List<T> records) {
        PageResult<T> result = new PageResult<>();
        result.setPage(page);
        result.setSize(size);
        result.setTotal(total);
        result.setPages((int) Math.ceil((double) total / size));
        result.setRecords(records);
        return result;
    }
}