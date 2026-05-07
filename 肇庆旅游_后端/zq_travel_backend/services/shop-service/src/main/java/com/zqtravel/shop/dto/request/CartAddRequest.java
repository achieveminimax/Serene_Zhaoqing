package com.zqtravel.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartAddRequest {

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Min(value = 1, message = "商品数量必须大于0")
    @Builder.Default
    private Integer quantity = 1;

    @Builder.Default
    private Boolean selected = true;
}