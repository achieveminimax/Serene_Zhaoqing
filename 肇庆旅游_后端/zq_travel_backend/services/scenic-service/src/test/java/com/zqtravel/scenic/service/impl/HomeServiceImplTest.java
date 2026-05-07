package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.dto.response.BannerDTO;
import com.zqtravel.scenic.dto.response.QuickActionDTO;
import com.zqtravel.scenic.dto.response.RecommendDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * HomeServiceImpl 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("首页服务测试")
class HomeServiceImplTest {

    @Mock
    private ScenicService scenicService;

    @Mock
    private ScenicSpotRepository scenicSpotRepository;

    @InjectMocks
    private HomeServiceImpl homeService;

    @BeforeEach
    void setUp() {
        // 设置配置属性默认值
        ReflectionTestUtils.setField(homeService, "bannerEnabled", true);
        ReflectionTestUtils.setField(homeService, "recommendEnabled", true);
        ReflectionTestUtils.setField(homeService, "quickActionEnabled", true);
    }

    @Test
    @DisplayName("获取轮播图列表 - 正常获取")
    void getBanners_WithEnabled_ShouldReturnBanners() {
        // When
        List<BannerDTO> result = homeService.getBanners();

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getTitle()).isEqualTo("七星岩美景");
        assertThat(result.get(0).getSortOrder()).isEqualTo(1);
    }

    @Test
    @DisplayName("获取轮播图列表 - 禁用状态")
    void getBanners_WithDisabled_ShouldReturnEmptyList() {
        // Given
        ReflectionTestUtils.setField(homeService, "bannerEnabled", false);

        // When
        List<BannerDTO> result = homeService.getBanners();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取首页推荐 - 正常获取")
    void getHomeRecommends_WithEnabled_ShouldReturnRecommends() {
        // When
        List<RecommendDTO> result = homeService.getHomeRecommends();

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getType()).isEqualTo("scenic");
        assertThat(result.get(1).getType()).isEqualTo("music");
        assertThat(result.get(2).getType()).isEqualTo("recipe");
    }

    @Test
    @DisplayName("获取首页推荐 - 禁用状态")
    void getHomeRecommends_WithDisabled_ShouldReturnEmptyList() {
        // Given
        ReflectionTestUtils.setField(homeService, "recommendEnabled", false);

        // When
        List<RecommendDTO> result = homeService.getHomeRecommends();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取首页景点推荐 - 指定数量")
    void getScenicListForHome_WithLimit_ShouldReturnLimitedSpots() {
        // Given
        Integer limit = 4;
        List<ScenicSpotDTO> spots = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            spots.add(TestDataFactory.createScenicSpotDTO((long) i, "景点" + i));
        }

        given(scenicService.getHotSpots(limit)).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = homeService.getScenicListForHome(limit);

        // Then
        assertThat(result).hasSize(4);
        verify(scenicService).getHotSpots(4);
    }

    @Test
    @DisplayName("获取首页景点推荐 - 默认数量")
    void getScenicListForHome_WithNullLimit_ShouldReturnDefaultSpots() {
        // Given
        List<ScenicSpotDTO> spots = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            spots.add(TestDataFactory.createScenicSpotDTO((long) i, "景点" + i));
        }

        given(scenicService.getHotSpots(6)).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = homeService.getScenicListForHome(null);

        // Then
        assertThat(result).hasSize(6);
        verify(scenicService).getHotSpots(6);
    }

    @Test
    @DisplayName("获取首页景点推荐 - 空结果")
    void getScenicListForHome_WithNoResults_ShouldReturnEmptyList() {
        // Given
        given(scenicService.getHotSpots(anyInt())).willReturn(Collections.emptyList());

        // When
        List<ScenicSpotDTO> result = homeService.getScenicListForHome(10);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取快捷操作入口 - 正常获取")
    void getQuickActions_WithEnabled_ShouldReturnActions() {
        // When
        List<QuickActionDTO> result = homeService.getQuickActions();

        // Then
        assertThat(result).hasSize(4);
        assertThat(result.get(0).getName()).isEqualTo("景点搜索");
        assertThat(result.get(1).getName()).isEqualTo("附近景点");
        assertThat(result.get(2).getName()).isEqualTo("我的收藏");
        assertThat(result.get(3).getName()).isEqualTo("行程规划");
    }

    @Test
    @DisplayName("获取快捷操作入口 - 禁用状态")
    void getQuickActions_WithDisabled_ShouldReturnEmptyList() {
        // Given
        ReflectionTestUtils.setField(homeService, "quickActionEnabled", false);

        // When
        List<QuickActionDTO> result = homeService.getQuickActions();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取首页统计数据 - 正常获取")
    void getHomeStats_WithNormalData_ShouldReturnStats() {
        // Given
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(50L);

        // When
        HomeService.HomeStats result = homeService.getHomeStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalSpots()).isEqualTo(50);
        assertThat(result.getTotalUsers()).isEqualTo(1000);
        assertThat(result.getTodayVisitors()).isEqualTo(150);
        assertThat(result.getTotalFavorites()).isEqualTo(5000);
    }

    @Test
    @DisplayName("获取首页统计数据 - 数据库返回null")
    void getHomeStats_WithNullCount_ShouldReturnZero() {
        // Given
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(null);

        // When
        HomeService.HomeStats result = homeService.getHomeStats();

        // Then
        assertThat(result.getTotalSpots()).isZero();
    }

    @Test
    @DisplayName("获取首页统计数据 - 异常处理")
    void getHomeStats_WithException_ShouldReturnDefaultStats() {
        // Given
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class)))
                .willThrow(new RuntimeException("数据库连接失败"));

        // When
        HomeService.HomeStats result = homeService.getHomeStats();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalSpots()).isZero();
        assertThat(result.getTotalUsers()).isZero();
        assertThat(result.getTodayVisitors()).isZero();
        assertThat(result.getTotalFavorites()).isZero();
    }
}
