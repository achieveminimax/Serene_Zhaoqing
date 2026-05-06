package com.zqtravel.scenic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqtravel.scenic.dto.request.NearbyScenicRequest;
import com.zqtravel.scenic.dto.request.ScenicQueryRequest;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 景点服务接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface ScenicService {

    /**
     * 分页查询景点列表
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<ScenicSpotDTO> getScenicList(ScenicQueryRequest request);

    /**
     * 获取景点详情
     *
     * @param id 景点ID
     * @param userId 用户ID（可选，用于判断是否收藏）
     * @return 景点详情
     */
    ScenicDetailVO getScenicDetail(Long id, Long userId);

    /**
     * 获取热门景点列表
     *
     * @param limit 限制数量
     * @return 热门景点列表
     */
    List<ScenicSpotDTO> getHotSpots(Integer limit);

    /**
     * 获取附近景点
     *
     * @param request 附近景点查询请求
     * @param userId 用户ID（可选，用于判断是否收藏）
     * @return 附近景点列表
     */
    List<ScenicSpotDTO> getNearbyScenic(NearbyScenicRequest request, Long userId);

    /**
     * 搜索景点
     *
     * @param keyword 关键词
     * @param limit 限制数量
     * @return 搜索结果
     */
    List<ScenicSpotDTO> searchScenic(String keyword, Integer limit);

    /**
     * 增加景点浏览计数
     *
     * @param id 景点ID
     */
    void incrementViewCount(Long id);

    /**
     * 检查景点是否存在
     *
     * @param id 景点ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 根据分类ID获取景点列表
     *
     * @param categoryId 分类ID
     * @param limit 限制数量
     * @return 景点列表
     */
    List<ScenicSpotDTO> getSpotsByCategoryId(Long categoryId, Integer limit);
}