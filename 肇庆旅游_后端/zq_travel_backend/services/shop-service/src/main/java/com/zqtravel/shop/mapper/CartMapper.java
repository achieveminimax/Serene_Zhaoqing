package com.zqtravel.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.shop.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 根据用户ID查询购物车列表
     */
    @Select("SELECT * FROM carts WHERE user_id = #{userId} ORDER BY updated_at DESC")
    List<Cart> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和商品ID查询购物车项
     */
    @Select("SELECT * FROM carts WHERE user_id = #{userId} AND product_id = #{productId}")
    Cart selectByUserIdAndProductId(@Param("userId") Long userId,
                                    @Param("productId") Long productId);

    /**
     * 删除用户购物车中选中的商品
     */
    @Select("DELETE FROM carts WHERE user_id = #{userId} AND selected = 1")
    int deleteSelectedByUserId(@Param("userId") Long userId);

    /**
     * 更新购物车项选中状态
     */
    @Select("UPDATE carts SET selected = #{selected} WHERE user_id = #{userId} AND product_id = #{productId}")
    int updateSelectedStatus(@Param("userId") Long userId,
                             @Param("productId") Long productId,
                             @Param("selected") Integer selected);

    /**
     * 清空用户购物车
     */
    @Select("DELETE FROM carts WHERE user_id = #{userId}")
    int clearByUserId(@Param("userId") Long userId);
}