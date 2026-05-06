package com.zqtravel.scenic.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.scenic.entity.UserFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户收藏数据访问接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface UserFavoriteRepository extends BaseMapper<UserFavorite> {

    /**
     * 查询用户收藏的景点
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（scenic）
     * @return 收藏列表
     */
    @Select("SELECT * FROM user_favorites WHERE user_id = #{userId} AND target_type = #{targetType} ORDER BY created_at DESC")
    List<UserFavorite> selectUserFavorites(@Param("userId") Long userId, @Param("targetType") String targetType);

    /**
     * 查询用户是否收藏了某个景点
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（scenic）
     * @param targetId 目标ID（景点ID）
     * @return 收藏记录，如果不存在返回null
     */
    @Select("SELECT * FROM user_favorites WHERE user_id = #{userId} AND target_type = #{targetType} AND target_id = #{targetId}")
    UserFavorite selectUserFavorite(@Param("userId") Long userId,
                                    @Param("targetType") String targetType,
                                    @Param("targetId") Long targetId);

    /**
     * 统计用户收藏数量
     *
     * @param userId 用户ID
     * @param targetType 收藏类型（scenic）
     * @return 收藏数量
     */
    @Select("SELECT COUNT(*) FROM user_favorites WHERE user_id = #{userId} AND target_type = #{targetType}")
    Integer countUserFavorites(@Param("userId") Long userId, @Param("targetType") String targetType);
}