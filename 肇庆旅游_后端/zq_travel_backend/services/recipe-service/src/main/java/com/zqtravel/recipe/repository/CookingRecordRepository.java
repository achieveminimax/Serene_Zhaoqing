package com.zqtravel.recipe.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.entity.CookingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 烹饪记录数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Mapper
public interface CookingRecordRepository extends BaseMapper<CookingRecord> {

    /**
     * 分页查询用户的烹饪记录
     * 
     * @param page 分页参数
     * @param userId 用户ID
     * @return 烹饪记录分页列表
     */
    IPage<CookingRecord> selectByUserId(Page<CookingRecord> page, @Param("userId") Long userId);

    /**
     * 查询用户对某个食谱的烹饪记录
     * 
     * @param userId 用户ID
     * @param recipeId 食谱ID
     * @return 烹饪记录列表
     */
    List<CookingRecord> selectByUserAndRecipe(@Param("userId") Long userId, @Param("recipeId") Long recipeId);

    /**
     * 统计用户烹饪次数
     * 
     * @param userId 用户ID
     * @return 烹饪次数
     */
    int countByUserId(@Param("userId") Long userId);

    /**
     * 查询用户最近烹饪的食谱
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 食谱ID列表
     */
    List<Long> selectRecentRecipeIds(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * 查询时间段内的烹饪记录
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 烹饪记录列表
     */
    List<CookingRecord> selectByTimeRange(@Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);
}