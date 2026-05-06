package com.zqtravel.scenic.service;

import com.zqtravel.scenic.dto.response.FavoriteDTO;

import java.util.List;

/**
 * 收藏服务接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface FavoriteService {

    /**
     * 添加收藏
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（scenic/music/recipe/product）
     * @param targetId 目标ID
     * @return 收藏记录ID
     */
    Long addFavorite(Long userId, String targetType, Long targetId);

    /**
     * 取消收藏
     *
     * @param userId 用户ID
     * @param targetType 收藏类型
     * @param targetId 目标ID
     * @return 是否成功取消
     */
    boolean removeFavorite(Long userId, String targetType, Long targetId);

    /**
     * 获取用户收藏列表
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（可选，为空时查询所有类型）
     * @return 收藏列表
     */
    List<FavoriteDTO> getUserFavorites(Long userId, String targetType);

    /**
     * 检查用户是否已收藏
     *
     * @param userId 用户ID
     * @param targetType 收藏类型
     * @param targetId 目标ID
     * @return 是否已收藏
     */
    boolean isFavorite(Long userId, String targetType, Long targetId);

    /**
     * 获取用户收藏数量
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（可选，为空时查询所有类型）
     * @return 收藏数量
     */
    Integer getUserFavoriteCount(Long userId, String targetType);

    /**
     * 切换收藏状态（如果已收藏则取消，如果未收藏则添加）
     *
     * @param userId 用户ID
     * @param targetType 收藏类型
     * @param targetId 目标ID
     * @return 操作后的收藏状态（true-已收藏，false-未收藏）
     */
    boolean toggleFavorite(Long userId, String targetType, Long targetId);
}