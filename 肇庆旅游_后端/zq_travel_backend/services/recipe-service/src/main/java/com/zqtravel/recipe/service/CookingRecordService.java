package com.zqtravel.recipe.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqtravel.recipe.dto.CookingRecordDTO;
import com.zqtravel.recipe.dto.CookingRecordRequest;

/**
 * 烹饪记录服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
public interface CookingRecordService {

    /**
     * 记录烹饪完成
     * 
     * @param recipeId 食谱ID
     * @param userId 用户ID
     * @param request 烹饪记录请求
     * @return 烹饪记录ID
     */
    Long recordCooking(Long recipeId, Long userId, CookingRecordRequest request);

    /**
     * 获取用户的烹饪记录
     * 
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 烹饪记录分页列表
     */
    IPage<CookingRecordDTO> getCookingRecords(Long userId, Integer page, Integer size);

    /**
     * 获取用户对某个食谱的烹饪记录
     * 
     * @param recipeId 食谱ID
     * @param userId 用户ID
     * @return 烹饪记录列表
     */
    CookingRecordDTO getCookingRecord(Long recipeId, Long userId);

    /**
     * 统计用户烹饪次数
     * 
     * @param userId 用户ID
     * @return 烹饪次数
     */
    int countCookingRecords(Long userId);

    /**
     * 获取用户最近烹饪的食谱ID
     * 
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 食谱ID列表
     */
    java.util.List<Long> getRecentRecipeIds(Long userId, int limit);
}