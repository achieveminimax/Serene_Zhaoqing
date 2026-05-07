package com.zqtravel.scenic.controller;

import com.zqtravel.scenic.dto.response.BannerDTO;
import com.zqtravel.scenic.dto.response.QuickActionDTO;
import com.zqtravel.scenic.dto.response.RecommendDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.service.HomeService;
import com.zqtravel.scenic.service.ScenicService;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * HomeController 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("首页控制器测试")
class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HomeService homeService;

    @Mock
    private ScenicService scenicService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    @DisplayName("获取轮播图列表 - 正常请求")
    void getBanners_ShouldReturnBanners() throws Exception {
        // Given
        List<BannerDTO> banners = new ArrayList<>();
        BannerDTO banner1 = TestDataFactory.createBannerDTO();
        banner1.setId(1L);
        banner1.setTitle("七星岩美景");
        banners.add(banner1);

        BannerDTO banner2 = TestDataFactory.createBannerDTO();
        banner2.setId(2L);
        banner2.setTitle("鼎湖山风光");
        banners.add(banner2);

        given(homeService.getBanners()).willReturn(banners);

        // When & Then
        mockMvc.perform(get("/api/v1/home/banners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].title").value("七星岩美景"));
    }

    @Test
    @DisplayName("获取首页推荐 - 正常请求")
    void getHomeRecommends_ShouldReturnRecommends() throws Exception {
        // Given
        List<RecommendDTO> recommends = new ArrayList<>();
        RecommendDTO recommend1 = TestDataFactory.createRecommendDTO();
        recommend1.setId(1L);
        recommend1.setTitle("热门景点推荐");
        recommend1.setType("scenic");
        recommends.add(recommend1);

        RecommendDTO recommend2 = TestDataFactory.createRecommendDTO();
        recommend2.setId(2L);
        recommend2.setTitle("疗愈音乐");
        recommend2.setType("music");
        recommends.add(recommend2);

        given(homeService.getHomeRecommends()).willReturn(recommends);

        // When & Then
        mockMvc.perform(get("/api/v1/home/recommends"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].type").value("scenic"));
    }

    @Test
    @DisplayName("获取首页景点推荐 - 指定数量")
    void getScenicListForHome_WithLimit_ShouldReturnScenicList() throws Exception {
        // Given
        Integer limit = 4;
        List<ScenicSpotDTO> spots = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            spots.add(TestDataFactory.createScenicSpotDTO((long) i, "景点" + i));
        }

        given(homeService.getScenicListForHome(limit)).willReturn(spots);

        // When & Then
        mockMvc.perform(get("/api/v1/home/scenic-list")
                        .param("limit", limit.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(4));
    }

    @Test
    @DisplayName("获取首页景点推荐 - 默认数量")
    void getScenicListForHome_WithDefaultLimit_ShouldReturnDefaultScenicList() throws Exception {
        // Given
        List<ScenicSpotDTO> spots = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            spots.add(TestDataFactory.createScenicSpotDTO((long) i, "景点" + i));
        }

        given(homeService.getScenicListForHome(6)).willReturn(spots);

        // When & Then
        mockMvc.perform(get("/api/v1/home/scenic-list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(6));
    }

    @Test
    @DisplayName("获取快捷操作入口 - 正常请求")
    void getQuickActions_ShouldReturnQuickActions() throws Exception {
        // Given
        List<QuickActionDTO> actions = new ArrayList<>();
        QuickActionDTO action1 = TestDataFactory.createQuickActionDTO();
        action1.setId(1L);
        action1.setName("景点搜索");
        actions.add(action1);

        QuickActionDTO action2 = TestDataFactory.createQuickActionDTO();
        action2.setId(2L);
        action2.setName("附近景点");
        actions.add(action2);

        given(homeService.getQuickActions()).willReturn(actions);

        // When & Then
        mockMvc.perform(get("/api/v1/home/quick-actions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("景点搜索"));
    }

    @Test
    @DisplayName("获取首页统计数据 - 正常请求")
    void getHomeStats_ShouldReturnStats() throws Exception {
        // Given
        HomeService.HomeStats stats = new HomeService.HomeStats();
        stats.setTotalSpots(50);
        stats.setTotalUsers(1000);
        stats.setTodayVisitors(150);
        stats.setTotalFavorites(5000);

        given(homeService.getHomeStats()).willReturn(stats);

        // When & Then
        mockMvc.perform(get("/api/v1/home/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalSpots").value(50))
                .andExpect(jsonPath("$.data.totalUsers").value(1000))
                .andExpect(jsonPath("$.data.todayVisitors").value(150))
                .andExpect(jsonPath("$.data.totalFavorites").value(5000));
    }

    @Test
    @DisplayName("获取热门景点 - 正常请求")
    void getHotSpotsForHome_WithLimit_ShouldReturnHotSpots() throws Exception {
        // Given
        Integer limit = 8;
        List<ScenicSpotDTO> spots = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            spots.add(TestDataFactory.createScenicSpotDTO((long) i, "热门景点" + i));
        }

        given(scenicService.getHotSpots(limit)).willReturn(spots);

        // When & Then
        mockMvc.perform(get("/api/v1/home/hot-spots")
                        .param("limit", limit.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(8));
    }

    @Test
    @DisplayName("获取首页综合数据 - 正常请求")
    void getHomeAllData_ShouldReturnAllData() throws Exception {
        // Given
        List<BannerDTO> banners = Collections.singletonList(TestDataFactory.createBannerDTO());
        List<RecommendDTO> recommends = Collections.singletonList(TestDataFactory.createRecommendDTO());
        List<ScenicSpotDTO> scenicList = Collections.singletonList(TestDataFactory.createScenicSpotDTO());
        List<QuickActionDTO> quickActions = Collections.singletonList(TestDataFactory.createQuickActionDTO());
        HomeService.HomeStats stats = new HomeService.HomeStats();
        stats.setTotalSpots(50);

        given(homeService.getBanners()).willReturn(banners);
        given(homeService.getHomeRecommends()).willReturn(recommends);
        given(homeService.getScenicListForHome(6)).willReturn(scenicList);
        given(homeService.getQuickActions()).willReturn(quickActions);
        given(homeService.getHomeStats()).willReturn(stats);

        // When & Then
        mockMvc.perform(get("/api/v1/home/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.banners").isArray())
                .andExpect(jsonPath("$.data.recommends").isArray())
                .andExpect(jsonPath("$.data.scenicList").isArray())
                .andExpect(jsonPath("$.data.quickActions").isArray())
                .andExpect(jsonPath("$.data.stats.totalSpots").value(50));
    }

    @Test
    @DisplayName("获取首页综合数据 - 指定景点数量")
    void getHomeAllData_WithScenicLimit_ShouldReturnAllData() throws Exception {
        // Given
        Integer scenicLimit = 4;
        List<BannerDTO> banners = Collections.singletonList(TestDataFactory.createBannerDTO());
        List<RecommendDTO> recommends = Collections.singletonList(TestDataFactory.createRecommendDTO());
        List<ScenicSpotDTO> scenicList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            scenicList.add(TestDataFactory.createScenicSpotDTO((long) i, "景点" + i));
        }
        List<QuickActionDTO> quickActions = Collections.singletonList(TestDataFactory.createQuickActionDTO());
        HomeService.HomeStats stats = new HomeService.HomeStats();

        given(homeService.getBanners()).willReturn(banners);
        given(homeService.getHomeRecommends()).willReturn(recommends);
        given(homeService.getScenicListForHome(scenicLimit)).willReturn(scenicList);
        given(homeService.getQuickActions()).willReturn(quickActions);
        given(homeService.getHomeStats()).willReturn(stats);

        // When & Then
        mockMvc.perform(get("/api/v1/home/all")
                        .param("scenicLimit", scenicLimit.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.scenicList").isArray())
                .andExpect(jsonPath("$.data.scenicList.length()").value(4));
    }
}
