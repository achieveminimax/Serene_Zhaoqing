package com.zqtravel.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.shop.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * 根据订单ID查询订单商品项
     */
    @Select("SELECT * FROM order_items WHERE order_id = #{orderId} ORDER BY created_at")
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    /**
     * 批量插入订单商品项
     */
    @Select("<script>" +
            "INSERT INTO order_items (order_id, product_id, product_name, product_image, price, quantity, total_amount) VALUES " +
            "<foreach collection='items' item='item' separator=','>" +
            "(#{item.orderId}, #{item.productId}, #{item.productName}, #{item.productImage}, #{item.price}, #{item.quantity}, #{item.totalAmount})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("items") List<OrderItem> items);

    /**
     * 统计商品销量
     */
    @Select("SELECT SUM(quantity) FROM order_items WHERE product_id = #{productId}")
    Integer sumSalesByProductId(@Param("productId") Long productId);
}