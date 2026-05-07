package com.zqtravel.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询商品列表
     */
    IPage<Product> selectProductPage(Page<Product> page,
                                     @Param("categoryId") Long categoryId,
                                     @Param("keyword") String keyword,
                                     @Param("minPrice") BigDecimal minPrice,
                                     @Param("maxPrice") BigDecimal maxPrice,
                                     @Param("status") Integer status);

    /**
     * 根据分类ID查询商品列表
     */
    @Select("SELECT * FROM products WHERE category_id = #{categoryId} AND status = 1 AND is_deleted = 0 ORDER BY created_at DESC")
    List<Product> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 搜索商品
     */
    @Select("SELECT * FROM products WHERE (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')) AND status = 1 AND is_deleted = 0 ORDER BY sales_count DESC")
    List<Product> searchProducts(@Param("keyword") String keyword);

    /**
     * 更新商品库存（乐观锁）
     */
    @Select("UPDATE products SET stock = stock - #{quantity}, version = version + 1 WHERE id = #{productId} AND stock >= #{quantity} AND version = #{version}")
    int updateStockWithOptimisticLock(@Param("productId") Long productId,
                                      @Param("quantity") Integer quantity,
                                      @Param("version") Integer version);

    /**
     * 增加商品销量
     */
    @Select("UPDATE products SET sales_count = sales_count + #{quantity} WHERE id = #{productId}")
    int increaseSalesCount(@Param("productId") Long productId,
                           @Param("quantity") Integer quantity);

    /**
     * 获取热门商品
     */
    @Select("SELECT * FROM products WHERE status = 1 AND is_deleted = 0 ORDER BY sales_count DESC, created_at DESC LIMIT #{limit}")
    List<Product> selectHotProducts(@Param("limit") Integer limit);
}