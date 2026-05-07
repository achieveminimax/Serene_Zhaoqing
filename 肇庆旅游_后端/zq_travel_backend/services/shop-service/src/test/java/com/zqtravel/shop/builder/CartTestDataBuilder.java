package com.zqtravel.shop.builder;

import com.zqtravel.shop.entity.Cart;

import java.time.LocalDateTime;

/**
 * 购物车测试数据构建器
 */
public class CartTestDataBuilder {

    /**
     * 创建默认购物车项构建器
     */
    public static Cart.CartBuilder defaultCart() {
        return Cart.builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .quantity(2)
                .selected(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());
    }

    /**
     * 创建指定用户的购物车项
     */
    public static Cart cartWithUserId(Long userId) {
        return defaultCart()
                .userId(userId)
                .build();
    }

    /**
     * 创建指定商品的购物车项
     */
    public static Cart cartWithProductId(Long productId) {
        return defaultCart()
                .productId(productId)
                .build();
    }

    /**
     * 创建指定数量的购物车项
     */
    public static Cart cartWithQuantity(Integer quantity) {
        return defaultCart()
                .quantity(quantity)
                .build();
    }

    /**
     * 创建未选中的购物车项
     */
    public static Cart unselectedCart() {
        return defaultCart()
                .selected(0)
                .build();
    }

    /**
     * 创建选中的购物车项
     */
    public static Cart selectedCart() {
        return defaultCart()
                .selected(1)
                .build();
    }

    /**
     * 创建指定用户和商品的购物车项
     */
    public static Cart cartWithUserAndProduct(Long userId, Long productId) {
        return defaultCart()
                .userId(userId)
                .productId(productId)
                .build();
    }
}