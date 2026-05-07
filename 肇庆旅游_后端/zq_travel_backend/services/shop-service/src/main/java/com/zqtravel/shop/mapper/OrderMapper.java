package com.zqtravel.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.shop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 分页查询用户订单
     */
    IPage<Order> selectUserOrders(Page<Order> page,
                                  @Param("userId") Long userId,
                                  @Param("status") String status);

    /**
     * 根据订单号查询订单
     */
    @Select("SELECT * FROM orders WHERE order_no = #{orderNo}")
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 更新订单状态
     */
    @Select("UPDATE orders SET status = #{status}, updated_at = NOW() WHERE id = #{orderId}")
    int updateStatus(@Param("orderId") Long orderId,
                     @Param("status") String status);

    /**
     * 更新订单支付信息
     */
    @Select("UPDATE orders SET status = 'paid', pay_type = #{payType}, pay_time = #{payTime}, updated_at = NOW() WHERE id = #{orderId} AND status = 'pending'")
    int updatePaymentInfo(@Param("orderId") Long orderId,
                          @Param("payType") String payType,
                          @Param("payTime") LocalDateTime payTime);

    /**
     * 查询超时未支付订单
     */
    @Select("SELECT * FROM orders WHERE status = 'pending' AND created_at < #{timeoutTime}")
    List<Order> selectTimeoutOrders(@Param("timeoutTime") LocalDateTime timeoutTime);

    /**
     * 统计用户订单数量
     */
    @Select("SELECT COUNT(*) FROM orders WHERE user_id = #{userId} AND status = #{status}")
    int countByUserIdAndStatus(@Param("userId") Long userId,
                               @Param("status") String status);
}