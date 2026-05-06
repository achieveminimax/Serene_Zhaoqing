package com.zqtravel.scenic.controller;

import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.scenic.dto.response.BannerDTO;
import com.zqtravel.scenic.dto.response.QuickActionDTO;
import com.zqtravel.scenic.dto.response.RecommendDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.service.HomeService;
import com.zqtravel.scenic.service.ScenicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页控制器
 * 实现首页相关接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
@Tag(name = "首页管理", description = "首页相关接口")
public class HomeController {

    private final HomeService homeService;
    private final ScenicService scenicService;

    /**
     * 获取轮播图列表
     * 接口编号：HOME-001
     */
    @Operation(summary = "获取轮播图列表", description = "获取首页轮播图列表")
    @GetMapping("/banners")
    public ApiResponse<List<BannerDTO>> getBanners() {
        log.info("获取轮播图列表");
        
        List<BannerDTO> banners = homeService.getBanners();
        return ApiResponse.success(banners);
    }

    /**
     * 获取首页推荐
     * 接口编号：HOME-002
     */
    @Operation(summary = "获取首页推荐", description = "获取首页推荐内容列表")
    @GetMapping("/recommends")
    public ApiResponse<List<RecommendDTO>> getHomeRecommends() {
        log.info("获取首页推荐内容");
        
        List<RecommendDTO> recommends = homeService.getHomeRecommends();
        return ApiResponse.success(recommends);
    }

    /**
     * 获取首页景点推荐
     * 接口编号：HOME-003
     */
    @Operation(summary = "获取首页景点推荐", description = "获取首页景点推荐列表")
    @GetMapping("/scenic-list")
    public ApiResponse<List<ScenicSpotDTO>> getScenicListForHome(
            @Parameter(description = "返回数量限制", example = "6")
            @RequestParam(defaultValue = "6") Integer limit
    ) {
        log.info("获取首页景点推荐，限制数量: {}", limit);
        
        List<ScenicSpotDTO> scenicList = homeService.getScenicListForHome(limit);
        return ApiResponse.success(scenicList);
    }

    /**
     * 获取快捷操作入口
     * 接口编号：HOME-004
     */
    @Operation(summary = "获取快捷操作入口", description = "获取首页快捷操作入口列表")
    @GetMapping("/quick-actions")
    public ApiResponse<List<QuickActionDTO>> getQuickActions() {
        log.info("获取快捷操作入口");
        
        List<QuickActionDTO> quickActions = homeService.getQuickActions();
        return ApiResponse.success(quickActions);
    }

    /**
     * 获取首页统计数据
     * 扩展接口
     */
    @Operation(summary = "获取首页统计数据", description = "获取首页统计数据（景点总数、用户总数等）")
    @GetMapping("/stats")
    public ApiResponse<HomeService.HomeStats> getHomeStats() {
        log.info("获取首页统计数据");
        
        HomeService.HomeStats stats = homeService.getHomeStats();
        return ApiResponse.success(stats);
    }

    /**
     * 获取热门景点（首页用）
     * 扩展接口
     */
    @Operation(summary = "获取热门景点", description = "获取热门景点列表，用于首页展示")
    @GetMapping("/hot-spots")
    public ApiResponse<List<ScenicSpotDTO>> getHotSpotsForHome(
            @Parameter(description = "返回数量限制", example = "8")
            @RequestParam(defaultValue = "8") Integer limit
    ) {
        log.info("获取热门景点（首页用），限制数量: {}", limit);
        
        List<ScenicSpotDTO> hotSpots = scenicService.getHotSpots(limit);
        return ApiResponse.success(hotSpots);
    }

    /**
     * 获取首页综合数据
     * 扩展接口：一次性获取首页所有数据
     */
    @Operation(summary = "获取首页综合数据", description = "一次性获取首页所有数据（轮播图、推荐、景点、快捷入口等）")
    @GetMapping("/all")
    public ApiResponse<HomeData> getHomeAllData(
            @Parameter(description = "景点推荐数量", example = "6")
            @RequestParam(defaultValue = "6") Integer scenicLimit
    ) {
        log.info("获取首页综合数据，景点推荐数量: {}", scenicLimit);
        
        HomeData homeData = new HomeData();
        homeData.setBanners(homeService.getBanners());
        homeData.setRecommends(homeService.getHomeRecommends());
        homeData.setScenicList(homeService.getScenicListForHome(scenicLimit));
        homeData.setQuickActions(homeService.getQuickActions());
        homeData.setStats(homeService.getHomeStats());
        
        return ApiResponse.success(homeData);
    }

    /**
     * 首页综合数据类
     */
    public static class HomeData {
        private List<BannerDTO> banners;
        private List<RecommendDTO> recommends;
        private List<ScenicSpotDTO> scenicList;
        private List<QuickActionDTO> quickActions;
        private HomeService.HomeStats stats;

        // getters and setters
        public List<BannerDTO> getBanners() {
            return banners;
        }

        public void setBanners(List<BannerDTO> banners) {
            this.banners = banners;
        }

        public List<RecommendDTO> getRecommends() {
            return recommends;
        }

        public void setRecommends(List<RecommendDTO> recommends) {
            this.recommends = recommends;
        }

        public List<ScenicSpotDTO> getScenicList() {
            return scenicList;
        }

        public void setScenicList(List<ScenicSpotDTO> scenicList) {
            this.scenicList = scenicList;
        }

        public List<QuickActionDTO> getQuickActions() {
            return quickActions;
        }

        public void setQuickActions(List<QuickActionDTO> quickActions) {
            this.quickActions = quickActions;
        }

        public HomeService.HomeStats getStats() {
            return stats;
        }

        public void setStats(HomeService.HomeStats stats) {
            this.stats = stats;
        }
    }
}