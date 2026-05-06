package com.zqtravel.scenic.converter;

import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;
import com.zqtravel.scenic.entity.ScenicCategory;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点分类转换器
 * 用于实体和DTO之间的转换
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@UtilityClass
public class ScenicCategoryConverter {

    /**
     * 将分类实体转换为DTO
     *
     * @param entity 分类实体
     * @return 分类DTO
     */
    public ScenicCategoryDTO toDTO(ScenicCategory entity) {
        if (entity == null) {
            return null;
        }

        ScenicCategoryDTO dto = new ScenicCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIcon(entity.getIcon());
        dto.setSortOrder(entity.getSortOrder());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }

    /**
     * 将分类实体列表转换为DTO列表
     *
     * @param entities 分类实体列表
     * @return 分类DTO列表
     */
    public List<ScenicCategoryDTO> toDTOList(List<ScenicCategory> entities) {
        List<ScenicCategoryDTO> dtos = new ArrayList<>();
        for (ScenicCategory entity : entities) {
            ScenicCategoryDTO dto = toDTO(entity);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }
}