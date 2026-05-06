package com.zqtravel.scenic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.scenic.dto.request.FavoriteRequest;
import com.zqtravel.scenic.dto.request.NearbyScenicRequest;
import com.zqtravel.scenic.dto.request.ScenicQueryRequest;
import com.zqtravel.scenic.dto.response.FavoriteDTO;
import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.service.FavoriteService;
import com.zqtravel.scenic.service.ScenicCategoryService;
import com.zqtravel.scenic.service.ScenicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;

/**
 * 景点控制器
 * 实现景点相关接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scenic")
@Tag(name = "景点管理", description = "景点相关接口")
public class ScenicController {

    private final ScenicService scenicService;
    private final ScenicCategoryService scenicCategoryService;
    private final FavoriteService favoriteService;

    /**
     * 获取景点列表
     * 接口编号：SCENIC-001
     */
    @Operation(summary = "获取景点列表", description = "分页查询景点列表，支持分类筛选、关键词搜索、排序")
    @GetMapping("/spots")
    public ApiResponse<IPage<ScenicSpotDTO>> getScenicList(
            @Valid ScenicQueryRequest request
    ) {
        log.info("获取景点列表，请求参数: {}", request);
        
        IPage<ScenicSpotDTO> result = scenicService.getScenicList(request);
        return ApiResponse.success(result);
    }

    /**
     * 获取景点详情
     * 接口编号：SCENIC-002
     */
    @Operation(summary = "获取景点详情", description = "根据景点ID获取景点详细信息")
    @GetMapping("/spots/{id}")
    public ApiResponse<ScenicDetailVO> getScenicDetail(
            @Parameter(description = "景点ID", required = true, example = "1")
            @PathVariable @Min(value = 1, message = "景点ID必须大于0") Long id,
            @Parameter(description = "用户ID（可选，用于判断是否收藏）", example = "1")
            @RequestHeader(value = "X-User-Id", required = false) Long userId
    ) {
        log.info("获取景点详情，景点ID: {}, 用户ID: {}", id, userId);
        
        ScenicDetailVO detail = scenicService.getScenicDetail(id, userId);
        return ApiResponse.success(detail);
    }

    /**
     * 获取景点分类
     * 接口编号：SCENIC-003
     */
    @Operation(summary = "获取景点分类", description = "获取所有景点分类列表")
    @GetMapping("/categories")
    public ApiResponse<List<ScenicCategoryDTO>> getScenicCategories() {
        log.info("获取景点分类列表");
        
        List<ScenicCategoryDTO> categories = scenicCategoryService.getAllCategories();
        return ApiResponse.success(categories);
    }

    /**
     * 收藏/取消收藏景点
     * 接口编号：SCENIC-004
     */
    @Operation(summary = "收藏/取消收藏景点", description = "用户收藏或取消收藏景点")
    @PostMapping("/spots/{id}/favorite")
    public ApiResponse<Boolean> toggleFavorite(
            @Parameter(description = "景点ID", required = true, example = "1")
            @PathVariable @Min(value = 1, message = "景点ID必须大于0") Long id,
            @Valid @RequestBody FavoriteRequest request
    ) {
        log.info("收藏/取消收藏景点，景点ID: {}, 请求参数: {}", id, request);
        
        boolean result;
        if ("add".equals(request.getAction())) {
            Long favoriteId = favoriteService.addFavorite(request.getUserId(), "scenic", id);
            result = favoriteId != null;
        } else if ("remove".equals(request.getAction())) {
            result = favoriteService.removeFavorite(request.getUserId(), "scenic", id);
        } else {
            // 切换收藏状态
            result = favoriteService.toggleFavorite(request.getUserId(), "scenic", id);
        }
        
        return ApiResponse.success(result);
    }

    /**
     * 获取收藏列表
     * 接口编号：SCENIC-005
     */
    @Operation(summary = "获取收藏列表", description = "获取用户收藏的景点列表")
    @GetMapping("/favorites")
    public ApiResponse<List<FavoriteDTO>> getFavoriteList(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestHeader("X-User-Id") Long userId
    ) {
        log.info("获取收藏列表，用户ID: {}", userId);
        
        List<FavoriteDTO> favorites = favoriteService.getUserFavorites(userId, "scenic");
        return ApiResponse.success(favorites);
    }

    /**
     * 获取附近景点
     * 接口编号：SCENIC-006
     */
    @Operation(summary = "获取附近景点", description = "根据地理位置获取附近的景点")
    @GetMapping("/nearby")
    public ApiResponse<List<ScenicSpotDTO>> getNearbyScenic(
            @Valid NearbyScenicRequest request,
            @Parameter(description = "用户ID（可选，用于判断是否收藏）", example = "1")
            @RequestHeader(value = "X-User-Id", required = false) Long userId
    ) {
        log.info("获取附近景点，请求参数: {}, 用户ID: {}", request, userId);
        
        List<ScenicSpotDTO> nearbySpots = scenicService.getNearbyScenic(request, userId);
        return ApiResponse.success(nearbySpots);
    }

    /**
     * 搜索景点
     * 扩展接口，不在M2要求中，但很有用
     */
    @Operation(summary = "搜索景点", description = "根据关键词搜索景点")
    @GetMapping("/search")
    public ApiResponse<List<ScenicSpotDTO>> searchScenic(
            @Parameter(description = "搜索关键词", required = true, example = "七星岩")
            @RequestParam String keyword,
            @Parameter(description = "返回数量限制", example = "10")
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        log.info("搜索景点，关键词: {}, 限制数量: {}", keyword, limit);
        
        List<ScenicSpotDTO> results = scenicService.searchScenic(keyword, limit);
        return ApiResponse.success(results);
    }

    /**
     * 获取热门景点
     * 扩展接口
     */
    @Operation(summary = "获取热门景点", description = "获取热门景点列表（按浏览量和收藏量排序）")
    @GetMapping("/hot")
    public ApiResponse<List<ScenicSpotDTO>> getHotSpots(
            @Parameter(description = "返回数量限制", example = "10")
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        log.info("获取热门景点，限制数量: {}", limit);
        
        List<ScenicSpotDTO> hotSpots = scenicService.getHotSpots(limit);
        return ApiResponse.success(hotSpots);
    }
}