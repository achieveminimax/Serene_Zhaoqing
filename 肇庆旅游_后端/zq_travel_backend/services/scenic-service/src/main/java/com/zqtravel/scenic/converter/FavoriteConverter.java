package com.zqtravel.scenic.converter;

import com.zqtravel.scenic.dto.response.FavoriteDTO;
import com.zqtravel.scenic.entity.UserFavorite;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏转换器
 * 用于实体和DTO之间的转换
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@UtilityClass
public class FavoriteConverter {

    /**
     * 将收藏实体转换为DTO
     *
     * @param entity 收藏实体
     * @return 收藏DTO
     */
    public FavoriteDTO toDTO(UserFavorite entity) {
        if (entity == null) {
            return null;
        }

        FavoriteDTO dto = new FavoriteDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setTargetType(entity.getTargetType());
        dto.setTargetId(entity.getTargetId());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }

    /**
     * 将收藏实体列表转换为DTO列表
     *
     * @param entities 收藏实体列表
     * @return 收藏DTO列表
     */
    public List<FavoriteDTO> toDTOList(List<UserFavorite> entities) {
        List<FavoriteDTO> dtos = new ArrayList<>();
        for (UserFavorite entity : entities) {
            FavoriteDTO dto = toDTO(entity);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }
}