package com.zqtravel.scenic.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.scenic.entity.ScenicSpot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * 景点数据访问接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface ScenicSpotRepository extends BaseMapper<ScenicSpot> {

    /**
     * 分页查询景点列表
     *
     * @param page 分页参数
     * @param categoryId 分类ID（可选）
     * @param status 状态（可选）
     * @param keyword 关键词（可选）
     * @return 分页结果
     */
    IPage<ScenicSpot> selectScenicPage(Page<ScenicSpot> page,
                                       @Param("categoryId") Long categoryId,
                                       @Param("status") Integer status,
                                       @Param("keyword") String keyword);

    /**
     * 根据分类ID查询景点列表
     *
     * @param categoryId 分类ID
     * @return 景点列表
     */
    @Select("SELECT * FROM scenic_spots WHERE category_id = #{categoryId} AND status = 1 AND is_deleted = 0 ORDER BY created_at DESC")
    List<ScenicSpot> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 查询热门景点（按浏览量和收藏量排序）
     *
     * @param limit 限制数量
     * @return 热门景点列表
     */
    @Select("SELECT * FROM scenic_spots WHERE status = 1 AND is_deleted = 0 ORDER BY view_count DESC, favorite_count DESC LIMIT #{limit}")
    List<ScenicSpot> selectHotSpots(@Param("limit") Integer limit);

    /**
     * 增加景点浏览计数
     *
     * @param id 景点ID
     * @return 更新影响的行数
     */
    @Select("UPDATE scenic_spots SET view_count = view_count + 1 WHERE id = #{id}")
    int incrementViewCount(@Param("id") Long id);

    /**
     * 增加景点收藏计数
     *
     * @param id 景点ID
     * @return 更新影响的行数
     */
    @Select("UPDATE scenic_spots SET favorite_count = favorite_count + 1 WHERE id = #{id}")
    int incrementFavoriteCount(@Param("id") Long id);

    /**
     * 减少景点收藏计数
     *
     * @param id 景点ID
     * @return 更新影响的行数
     */
    @Select("UPDATE scenic_spots SET favorite_count = favorite_count - 1 WHERE id = #{id} AND favorite_count > 0")
    int decrementFavoriteCount(@Param("id") Long id);

    /**
     * 查询附近的景点（基于经纬度）
     * 使用MySQL的空间函数计算距离
     *
     * @param lat 纬度
     * @param lng 经度
     * @param radius 半径（公里）
     * @param limit 限制数量
     * @return 附近的景点列表
     */
    @Select("SELECT *, " +
            "ROUND(6371.009 * ACOS(COS(RADIANS(#{lat})) * COS(RADIANS(location_lat)) * " +
            "COS(RADIANS(location_lng) - RADIANS(#{lng})) + SIN(RADIANS(#{lat})) * " +
            "SIN(RADIANS(location_lat))), 2) AS distance " +
            "FROM scenic_spots " +
            "WHERE status = 1 AND is_deleted = 0 " +
            "HAVING distance <= #{radius} " +
            "ORDER BY distance " +
            "LIMIT #{limit}")
    List<ScenicSpot> selectNearbySpots(@Param("lat") BigDecimal lat,
                                       @Param("lng") BigDecimal lng,
                                       @Param("radius") Double radius,
                                       @Param("limit") Integer limit);

    /**
     * 检查景点是否存在
     *
     * @param id 景点ID
     * @return 是否存在
     */
    default boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return selectById(id) != null;
    }
}