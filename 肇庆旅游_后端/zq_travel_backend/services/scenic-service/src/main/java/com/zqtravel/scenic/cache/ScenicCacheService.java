package com.zqtravel.scenic.cache;

import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;

import java.util.List;

/**
 * 景点缓存服务接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface ScenicCacheService {

    /**
     * 获取景点详情缓存
     *
     * @param id 景点ID
     * @return 景点详情，如果缓存不存在返回null
     */
    ScenicDetailVO getScenicDetail(Long id);

    /**
     * 设置景点详情缓存
     *
     * @param id 景点ID
     * @param detail 景点详情
     */
    void setScenicDetail(Long id, ScenicDetailVO detail);

    /**
     * 删除景点详情缓存
     *
     * @param id 景点ID
     */
    void deleteScenicDetail(Long id);

    /**
     * 获取热门景点缓存
     *
     * @param limit 限制数量
     * @return 热门景点列表，如果缓存不存在返回null
     */
    List<ScenicSpotDTO> getHotSpots(Integer limit);

    /**
     * 设置热门景点缓存
     *
     * @param limit 限制数量
     * @param spots 景点列表
     */
    void setHotSpots(Integer limit, List<ScenicSpotDTO> spots);

    /**
     * 删除热门景点缓存
     *
     * @param limit 限制数量
     */
    void deleteHotSpots(Integer limit);

    /**
     * 获取分类景点缓存
     *
     * @param categoryId 分类ID
     * @param limit 限制数量
     * @return 景点列表，如果缓存不存在返回null
     */
    List<ScenicSpotDTO> getSpotsByCategory(Long categoryId, Integer limit);

    /**
     * 设置分类景点缓存
     *
     * @param categoryId 分类ID
     * @param limit 限制数量
     * @param spots 景点列表
     */
    void setSpotsByCategory(Long categoryId, Integer limit, List<ScenicSpotDTO> spots);

    /**
     * 删除分类景点缓存
     *
     * @param categoryId 分类ID
     * @param limit 限制数量
     */
    void deleteSpotsByCategory(Long categoryId, Integer limit);

    /**
     * 清除所有景点相关缓存
     */
    void clearAllScenicCache();

    /**
     * 获取缓存键前缀
     *
     * @return 缓存键前缀
     */
    String getCacheKeyPrefix();
}