package com.zqtravel.shop.service;

import com.zqtravel.shop.dto.request.CartAddRequest;
import com.zqtravel.shop.dto.request.CartUpdateRequest;
import com.zqtravel.shop.dto.response.CartDTO;

import java.util.List;

public interface CartService {

    /**
     * 获取用户购物车
     */
    CartDTO.CartSummary getUserCart(Long userId);

    /**
     * 添加商品到购物车
     */
    void addToCart(Long userId, CartAddRequest request);

    /**
     * 更新购物车项
     */
    void updateCartItem(Long userId, Long itemId, CartUpdateRequest request);

    /**
     * 删除购物车项
     */
    void removeCartItem(Long userId, Long itemId);

    /**
     * 清空用户购物车
     */
    void clearUserCart(Long userId);

    /**
     * 合并购物车（登录后合并本地购物车）
     */
    void mergeCart(Long userId, List<CartAddRequest> localCartItems);
}