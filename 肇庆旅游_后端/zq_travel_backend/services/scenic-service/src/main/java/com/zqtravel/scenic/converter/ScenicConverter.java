package com.zqtravel.scenic.converter;

import cn.hutool.json.JSONUtil;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.entity.ScenicSpot;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点转换器
 * 用于实体和DTO之间的转换
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@UtilityClass
public class ScenicConverter {

    /**
     * 将景点实体转换为DTO
     *
     * @param entity 景点实体
     * @return 景点DTO
     */
    public ScenicSpotDTO toDTO(ScenicSpot entity) {
        if (entity == null) {
            return null;
        }

        ScenicSpotDTO dto = new ScenicSpotDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategoryId(entity.getCategoryId());
        dto.setHeroImage(entity.getHeroImage());
        
        // 解析JSON格式的图片列表
        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            try {
                List<String> images = JSONUtil.toList(entity.getImages(), String.class);
                dto.setImages(images);
            } catch (Exception e) {
                dto.setImages(new ArrayList<>());
            }
        } else {
            dto.setImages(new ArrayList<>());
        }
        
        dto.setAqi(entity.getAqi());
        dto.setTemperature(entity.getTemperature());
        dto.setHumidity(entity.getHumidity());
        dto.setQuote(entity.getQuote());
        dto.setDescription(entity.getDescription());
        dto.setDescriptionSecondary(entity.getDescriptionSecondary());
        dto.setOpenTime(entity.getOpenTime());
        dto.setDifficulty(entity.getDifficulty());
        dto.setDistance(entity.getDistance());
        dto.setLocationLat(entity.getLocationLat());
        dto.setLocationLng(entity.getLocationLng());
        dto.setAddress(entity.getAddress());
        dto.setStatus(entity.getStatus());
        dto.setViewCount(entity.getViewCount());
        dto.setFavoriteCount(entity.getFavoriteCount());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        return dto;
    }

    /**
     * 将景点实体列表转换为DTO列表
     *
     * @param entities 景点实体列表
     * @return 景点DTO列表
     */
    public List<ScenicSpotDTO> toDTOList(List<ScenicSpot> entities) {
        List<ScenicSpotDTO> dtos = new ArrayList<>();
        for (ScenicSpot entity : entities) {
            ScenicSpotDTO dto = toDTO(entity);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }

    /**
     * 将景点实体转换为详情视图对象
     *
     * @param entity 景点实体
     * @return 景点详情视图对象
     */
    public ScenicDetailVO toDetailVO(ScenicSpot entity) {
        if (entity == null) {
            return null;
        }

        ScenicDetailVO vo = new ScenicDetailVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setCategoryId(entity.getCategoryId());
        vo.setHeroImage(entity.getHeroImage());
        
        // 解析JSON格式的图片列表
        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            try {
                List<String> images = JSONUtil.toList(entity.getImages(), String.class);
                vo.setImages(images);
            } catch (Exception e) {
                vo.setImages(new ArrayList<>());
            }
        } else {
            vo.setImages(new ArrayList<>());
        }
        
        vo.setAqi(entity.getAqi());
        vo.setTemperature(entity.getTemperature());
        vo.setHumidity(entity.getHumidity());
        vo.setQuote(entity.getQuote());
        vo.setDescription(entity.getDescription());
        vo.setDescriptionSecondary(entity.getDescriptionSecondary());
        vo.setOpenTime(entity.getOpenTime());
        vo.setDifficulty(entity.getDifficulty());
        vo.setDistance(entity.getDistance());
        vo.setLocationLat(entity.getLocationLat());
        vo.setLocationLng(entity.getLocationLng());
        vo.setAddress(entity.getAddress());
        vo.setStatus(entity.getStatus());
        vo.setViewCount(entity.getViewCount());
        vo.setFavoriteCount(entity.getFavoriteCount());
        vo.setCreatedAt(entity.getCreatedAt());
        vo.setUpdatedAt(entity.getUpdatedAt());
        
        // 设置空气质量等级
        if (entity.getAqi() != null) {
            vo.setAqiLevel(getAqiLevel(entity.getAqi()));
        }
        
        return vo;
    }

    /**
     * 根据AQI值获取空气质量等级
     *
     * @param aqi AQI值
     * @return 空气质量等级
     */
    private String getAqiLevel(Integer aqi) {
        if (aqi == null) {
            return "未知";
        }
        if (aqi <= 50) {
            return "优";
        } else if (aqi <= 100) {
            return "良";
        } else if (aqi <= 150) {
            return "轻度污染";
        } else if (aqi <= 200) {
            return "中度污染";
        } else if (aqi <= 300) {
            return "重度污染";
        } else {
            return "严重污染";
        }
    }
}