package com.zqtravel.shop.service;

import com.zqtravel.shop.dto.PageResult;
import com.zqtravel.shop.dto.request.OrderCreateRequest;
import com.zqtravel.shop.dto.response.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO createOrder(Long userId, OrderCreateRequest request);

    /**
     * 获取订单详情
     */
    OrderDTO getOrderById(Long orderId);

    /**
     * 获取用户订单列表
     */
    PageResult<OrderDTO> getUserOrders(Long userId, Integer page, Integer size, String status);

    /**
     * 取消订单
     */
    void cancelOrder(Long orderId);

    /**
     * 支付订单
     */
    void payOrder(Long orderId, String payType);

    /**
     * 生成订单号
     */
    String generateOrderNo();
}