package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.scenic.dto.request.NearbyScenicRequest;
import com.zqtravel.scenic.dto.request.ScenicQueryRequest;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.entity.ScenicSpot;
import com.zqtravel.scenic.exception.ScenicNotFoundException;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.service.FavoriteService;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * ScenicServiceImpl 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("景点服务测试")
class ScenicServiceImplTest {

    @Mock
    private ScenicSpotRepository scenicSpotRepository;

    @Mock
    private FavoriteService favoriteService;

    @InjectMocks
    private ScenicServiceImpl scenicService;

    @BeforeEach
    void setUp() {
        // 初始化工作已在@InjectMocks中完成
    }

    @Test
    @DisplayName("获取景点列表 - 正常查询")
    void getScenicList_WithDefaultParams_ShouldReturnPage() {
        // Given
        ScenicQueryRequest request = TestDataFactory.createScenicQueryRequest();
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(3);
        Page<ScenicSpot> page = new Page<>(1, 20, 3);
        page.setRecords(spots);

        given(scenicSpotRepository.selectPage(any(Page.class), any(QueryWrapper.class)))
                .willReturn(page);

        // When
        IPage<ScenicSpotDTO> result = scenicService.getScenicList(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(3);
        assertThat(result.getTotal()).isEqualTo(3);
        verify(scenicSpotRepository).selectPage(any(Page.class), any(QueryWrapper.class));
    }

    @Test
    @DisplayName("获取景点列表 - 按分类筛选")
    void getScenicList_WithCategoryId_ShouldFilterByCategory() {
        // Given
        ScenicQueryRequest request = TestDataFactory.createScenicQueryRequest();
        request.setCategoryId(1L);
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(2);
        Page<ScenicSpot> page = new Page<>(1, 20, 2);
        page.setRecords(spots);

        given(scenicSpotRepository.selectPage(any(Page.class), any(QueryWrapper.class)))
                .willReturn(page);

        // When
        IPage<ScenicSpotDTO> result = scenicService.getScenicList(request);

        // Then
        assertThat(result.getRecords()).hasSize(2);
        verify(scenicSpotRepository).selectPage(any(Page.class), any(QueryWrapper.class));
    }

    @Test
    @DisplayName("获取景点列表 - 关键词搜索")
    void getScenicList_WithKeyword_ShouldSearchByKeyword() {
        // Given
        ScenicQueryRequest request = TestDataFactory.createScenicQueryRequest();
        request.setKeyword("七星岩");
        List<ScenicSpot> spots = Collections.singletonList(TestDataFactory.createScenicSpot());
        Page<ScenicSpot> page = new Page<>(1, 20, 1);
        page.setRecords(spots);

        given(scenicSpotRepository.selectPage(any(Page.class), any(QueryWrapper.class)))
                .willReturn(page);

        // When
        IPage<ScenicSpotDTO> result = scenicService.getScenicList(request);

        // Then
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0).getName()).contains("七星岩");
    }

    @Test
    @DisplayName("获取景点列表 - 空结果")
    void getScenicList_WithNoResults_ShouldReturnEmptyPage() {
        // Given
        ScenicQueryRequest request = TestDataFactory.createScenicQueryRequest();
        Page<ScenicSpot> page = new Page<>(1, 20, 0);
        page.setRecords(new ArrayList<>());

        given(scenicSpotRepository.selectPage(any(Page.class), any(QueryWrapper.class)))
                .willReturn(page);

        // When
        IPage<ScenicSpotDTO> result = scenicService.getScenicList(request);

        // Then
        assertThat(result.getRecords()).isEmpty();
        assertThat(result.getTotal()).isZero();
    }

    @Test
    @DisplayName("获取景点详情 - 正常获取")
    void getScenicDetail_WithExistingId_ShouldReturnDetail() {
        // Given
        Long spotId = 1L;
        Long userId = 1L;
        ScenicSpot spot = TestDataFactory.createScenicSpot(spotId, "七星岩");

        given(scenicSpotRepository.selectById(spotId)).willReturn(spot);
        given(scenicSpotRepository.incrementViewCount(spotId)).willReturn(1);
        given(favoriteService.isFavorite(userId, "scenic", spotId)).willReturn(true);

        // When
        ScenicDetailVO result = scenicService.getScenicDetail(spotId, userId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(spotId);
        assertThat(result.getName()).isEqualTo("七星岩");
        assertThat(result.getIsFavorite()).isTrue();
        verify(scenicSpotRepository).incrementViewCount(spotId);
    }

    @Test
    @DisplayName("获取景点详情 - 未登录用户")
    void getScenicDetail_WithNullUserId_ShouldNotCheckFavorite() {
        // Given
        Long spotId = 1L;
        ScenicSpot spot = TestDataFactory.createScenicSpot(spotId, "七星岩");

        given(scenicSpotRepository.selectById(spotId)).willReturn(spot);
        given(scenicSpotRepository.incrementViewCount(spotId)).willReturn(1);

        // When
        ScenicDetailVO result = scenicService.getScenicDetail(spotId, null);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIsFavorite()).isFalse();
        verify(favoriteService, never()).isFavorite(any(), any(), any());
    }

    @Test
    @DisplayName("获取景点详情 - 景点不存在")
    void getScenicDetail_WithNonExistingId_ShouldThrowException() {
        // Given
        Long spotId = 999L;
        given(scenicSpotRepository.selectById(spotId)).willReturn(null);

        // When & Then
        assertThatThrownBy(() -> scenicService.getScenicDetail(spotId, null))
                .isInstanceOf(ScenicNotFoundException.class)
                .hasMessageContaining("景点不存在");
    }

    @Test
    @DisplayName("获取景点详情 - 景点已删除")
    void getScenicDetail_WithDeletedSpot_ShouldThrowException() {
        // Given
        Long spotId = 1L;
        ScenicSpot spot = TestDataFactory.createScenicSpot(spotId, "七星岩");
        spot.setIsDeleted(1);

        given(scenicSpotRepository.selectById(spotId)).willReturn(spot);

        // When & Then
        assertThatThrownBy(() -> scenicService.getScenicDetail(spotId, null))
                .isInstanceOf(ScenicNotFoundException.class);
    }

    @Test
    @DisplayName("获取热门景点 - 正常获取")
    void getHotSpots_WithLimit_ShouldReturnHotSpots() {
        // Given
        Integer limit = 5;
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(5);

        given(scenicSpotRepository.selectHotSpots(limit)).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.getHotSpots(limit);

        // Then
        assertThat(result).hasSize(5);
        verify(scenicSpotRepository).selectHotSpots(limit);
    }

    @Test
    @DisplayName("获取热门景点 - 空结果")
    void getHotSpots_WithNoResults_ShouldReturnEmptyList() {
        // Given
        Integer limit = 10;
        given(scenicSpotRepository.selectHotSpots(limit)).willReturn(new ArrayList<>());

        // When
        List<ScenicSpotDTO> result = scenicService.getHotSpots(limit);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取附近景点 - 正常获取")
    void getNearbyScenic_WithValidLocation_ShouldReturnNearbySpots() {
        // Given
        NearbyScenicRequest request = TestDataFactory.createNearbyScenicRequest();
        Long userId = 1L;
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(3);

        given(scenicSpotRepository.selectNearbySpots(
                request.getLat(), request.getLng(), request.getRadius(), request.getLimit()))
                .willReturn(spots);
        given(favoriteService.isFavorite(any(), eq("scenic"), any())).willReturn(false);

        // When
        List<ScenicSpotDTO> result = scenicService.getNearbyScenic(request, userId);

        // Then
        assertThat(result).hasSize(3);
        verify(scenicSpotRepository).selectNearbySpots(
                request.getLat(), request.getLng(), request.getRadius(), request.getLimit());
    }

    @Test
    @DisplayName("获取附近景点 - 未登录用户")
    void getNearbyScenic_WithNullUserId_ShouldNotCheckFavorite() {
        // Given
        NearbyScenicRequest request = TestDataFactory.createNearbyScenicRequest();
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(2);

        given(scenicSpotRepository.selectNearbySpots(
                request.getLat(), request.getLng(), request.getRadius(), request.getLimit()))
                .willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.getNearbyScenic(request, null);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIsFavorite()).isFalse();
        verify(favoriteService, never()).isFavorite(any(), any(), any());
    }

    @Test
    @DisplayName("获取附近景点 - 空结果")
    void getNearbyScenic_WithNoResults_ShouldReturnEmptyList() {
        // Given
        NearbyScenicRequest request = TestDataFactory.createNearbyScenicRequest();
        given(scenicSpotRepository.selectNearbySpots(
                request.getLat(), request.getLng(), request.getRadius(), request.getLimit()))
                .willReturn(new ArrayList<>());

        // When
        List<ScenicSpotDTO> result = scenicService.getNearbyScenic(request, null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("搜索景点 - 正常搜索")
    void searchScenic_WithKeyword_ShouldReturnResults() {
        // Given
        String keyword = "七星岩";
        Integer limit = 10;
        List<ScenicSpot> spots = Collections.singletonList(TestDataFactory.createScenicSpot());

        given(scenicSpotRepository.selectList(any(QueryWrapper.class))).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.searchScenic(keyword, limit);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains("七星岩");
    }

    @Test
    @DisplayName("搜索景点 - 空关键词")
    void searchScenic_WithEmptyKeyword_ShouldReturnResults() {
        // Given
        String keyword = "";
        Integer limit = 10;
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(3);

        given(scenicSpotRepository.selectList(any(QueryWrapper.class))).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.searchScenic(keyword, limit);

        // Then
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("增加浏览计数")
    void incrementViewCount_ShouldCallRepository() {
        // Given
        Long spotId = 1L;
        given(scenicSpotRepository.incrementViewCount(spotId)).willReturn(1);

        // When
        scenicService.incrementViewCount(spotId);

        // Then
        verify(scenicSpotRepository).incrementViewCount(spotId);
    }

    @Test
    @DisplayName("检查景点是否存在 - 存在的ID")
    void existsById_WithExistingId_ShouldReturnTrue() {
        // Given
        Long spotId = 1L;
        ScenicSpot spot = TestDataFactory.createScenicSpot(spotId, "七星岩");
        given(scenicSpotRepository.selectById(spotId)).willReturn(spot);

        // When
        boolean result = scenicService.existsById(spotId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("检查景点是否存在 - 不存在的ID")
    void existsById_WithNonExistingId_ShouldReturnFalse() {
        // Given
        Long spotId = 999L;
        given(scenicSpotRepository.selectById(spotId)).willReturn(null);

        // When
        boolean result = scenicService.existsById(spotId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("检查景点是否存在 - null ID")
    void existsById_WithNullId_ShouldReturnFalse() {
        // When
        boolean result = scenicService.existsById(null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("检查景点是否存在 - 已删除的景点")
    void existsById_WithDeletedSpot_ShouldReturnFalse() {
        // Given
        Long spotId = 1L;
        ScenicSpot spot = TestDataFactory.createScenicSpot(spotId, "七星岩");
        spot.setIsDeleted(1);
        given(scenicSpotRepository.selectById(spotId)).willReturn(spot);

        // When
        boolean result = scenicService.existsById(spotId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("根据分类ID获取景点 - 正常获取")
    void getSpotsByCategoryId_WithValidCategory_ShouldReturnSpots() {
        // Given
        Long categoryId = 1L;
        Integer limit = 10;
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(3);

        given(scenicSpotRepository.selectList(any(QueryWrapper.class))).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.getSpotsByCategoryId(categoryId, limit);

        // Then
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("根据分类ID获取景点 - limit为null")
    void getSpotsByCategoryId_WithNullLimit_ShouldReturnAllSpots() {
        // Given
        Long categoryId = 1L;
        List<ScenicSpot> spots = TestDataFactory.createScenicSpotList(5);

        given(scenicSpotRepository.selectList(any(QueryWrapper.class))).willReturn(spots);

        // When
        List<ScenicSpotDTO> result = scenicService.getSpotsByCategoryId(categoryId, null);

        // Then
        assertThat(result).hasSize(5);
    }

    @Test
    @DisplayName("根据分类ID获取景点 - 空结果")
    void getSpotsByCategoryId_WithNoResults_ShouldReturnEmptyList() {
        // Given
        Long categoryId = 999L;
        Integer limit = 10;
        given(scenicSpotRepository.selectList(any(QueryWrapper.class))).willReturn(new ArrayList<>());

        // When
        List<ScenicSpotDTO> result = scenicService.getSpotsByCategoryId(categoryId, limit);

        // Then
        assertThat(result).isEmpty();
    }
}