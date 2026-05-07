package com.zqtravel.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQueryRequest {

    @Min(value = 1, message = "页码必须大于0")
    @Builder.Default
    private Integer page = 1;

    @Min(value = 1, message = "每页大小必须大于0")
    @Builder.Default
    private Integer size = 20;

    private Long categoryId;

    private String keyword;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String sortBy; // price, sales, created

    private String order; // asc, desc

    private Integer status; // 0-下架 1-上架
}