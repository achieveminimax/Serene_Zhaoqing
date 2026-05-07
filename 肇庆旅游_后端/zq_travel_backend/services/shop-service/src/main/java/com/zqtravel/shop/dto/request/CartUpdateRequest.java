package com.zqtravel.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateRequest {

    @Min(value = 1, message = "商品数量必须大于0")
    private Integer quantity;

    private Boolean selected;
}