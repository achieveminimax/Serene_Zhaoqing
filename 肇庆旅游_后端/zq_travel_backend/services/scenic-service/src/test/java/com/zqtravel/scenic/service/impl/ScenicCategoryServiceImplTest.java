package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;
import com.zqtravel.scenic.entity.ScenicCategory;
import com.zqtravel.scenic.repository.ScenicCategoryRepository;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.util.TestDataFactory;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * ScenicCategoryServiceImpl 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("景点分类服务测试")
class ScenicCategoryServiceImplTest {

    @Mock
    private ScenicCategoryRepository scenicCategoryRepository;

    @Mock
    private ScenicSpotRepository scenicSpotRepository;

    @InjectMocks
    private ScenicCategoryServiceImpl scenicCategoryService;

    @Test
    @DisplayName("获取所有分类 - 正常获取")
    void getAllCategories_WithExistingCategories_ShouldReturnCategories() {
        // Given
        List<ScenicCategory> categories = TestDataFactory.createScenicCategoryList(3);

        given(scenicCategoryRepository.selectAllCategories()).willReturn(categories);
        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(categories.get(0));
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(5L);

        // When
        List<ScenicCategoryDTO> result = scenicCategoryService.getAllCategories();

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getSpotCount()).isEqualTo(5);
        verify(scenicCategoryRepository).selectAllCategories();
    }

    @Test
    @DisplayName("获取所有分类 - 空列表")
    void getAllCategories_WithNoCategories_ShouldReturnEmptyList() {
        // Given
        given(scenicCategoryRepository.selectAllCategories()).willReturn(new ArrayList<>());

        // When
        List<ScenicCategoryDTO> result = scenicCategoryService.getAllCategories();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("获取所有分类 - 单个分类")
    void getAllCategories_WithSingleCategory_ShouldReturnSingleCategory() {
        // Given
        List<ScenicCategory> categories = Collections.singletonList(
                TestDataFactory.createScenicCategory(1L, "自然风光")
        );

        given(scenicCategoryRepository.selectAllCategories()).willReturn(categories);
        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(categories.get(0));
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(10L);

        // When
        List<ScenicCategoryDTO> result = scenicCategoryService.getAllCategories();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("自然风光");
        assertThat(result.get(0).getSpotCount()).isEqualTo(10);
    }

    @Test
    @DisplayName("根据ID获取分类 - 存在的ID")
    void getCategoryById_WithExistingId_ShouldReturnCategory() {
        // Given
        Long categoryId = 1L;
        ScenicCategory category = TestDataFactory.createScenicCategory(categoryId, "自然风光");

        given(scenicCategoryRepository.selectById(categoryId)).willReturn(category);
        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(category);
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(8L);

        // When
        ScenicCategoryDTO result = scenicCategoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(categoryId);
        assertThat(result.getName()).isEqualTo("自然风光");
        assertThat(result.getSpotCount()).isEqualTo(8);
    }

    @Test
    @DisplayName("根据ID获取分类 - 不存在的ID")
    void getCategoryById_WithNonExistingId_ShouldReturnNull() {
        // Given
        Long categoryId = 999L;
        given(scenicCategoryRepository.selectById(categoryId)).willReturn(null);

        // When
        ScenicCategoryDTO result = scenicCategoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("根据ID获取分类 - null ID")
    void getCategoryById_WithNullId_ShouldReturnNull() {
        // When
        ScenicCategoryDTO result = scenicCategoryService.getCategoryById(null);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("检查分类是否存在 - 存在的ID")
    void existsById_WithExistingId_ShouldReturnTrue() {
        // Given
        Long categoryId = 1L;
        ScenicCategory category = TestDataFactory.createScenicCategory(categoryId, "自然风光");
        given(scenicCategoryRepository.selectById(categoryId)).willReturn(category);

        // When
        boolean result = scenicCategoryService.existsById(categoryId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("检查分类是否存在 - 不存在的ID")
    void existsById_WithNonExistingId_ShouldReturnFalse() {
        // Given
        Long categoryId = 999L;
        given(scenicCategoryRepository.selectById(categoryId)).willReturn(null);

        // When
        boolean result = scenicCategoryService.existsById(categoryId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("检查分类是否存在 - null ID")
    void existsById_WithNullId_ShouldReturnFalse() {
        // When
        boolean result = scenicCategoryService.existsById(null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("获取分类下景点数量 - 存在的分类")
    void getSpotCountByCategoryId_WithExistingCategory_ShouldReturnCount() {
        // Given
        Long categoryId = 1L;
        ScenicCategory category = TestDataFactory.createScenicCategory(categoryId, "自然风光");

        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(category);
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(15L);

        // When
        Integer result = scenicCategoryService.getSpotCountByCategoryId(categoryId);

        // Then
        assertThat(result).isEqualTo(15);
    }

    @Test
    @DisplayName("获取分类下景点数量 - 不存在的分类")
    void getSpotCountByCategoryId_WithNonExistingCategory_ShouldReturnZero() {
        // Given
        Long categoryId = 999L;
        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(null);

        // When
        Integer result = scenicCategoryService.getSpotCountByCategoryId(categoryId);

        // Then
        assertThat(result).isZero();
    }

    @Test
    @DisplayName("获取分类下景点数量 - 数量为0")
    void getSpotCountByCategoryId_WithZeroCount_ShouldReturnZero() {
        // Given
        Long categoryId = 1L;
        ScenicCategory category = TestDataFactory.createScenicCategory(categoryId, "自然风光");

        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(category);
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(0L);

        // When
        Integer result = scenicCategoryService.getSpotCountByCategoryId(categoryId);

        // Then
        assertThat(result).isZero();
    }

    @Test
    @DisplayName("获取分类下景点数量 - null结果")
    void getSpotCountByCategoryId_WithNullCount_ShouldReturnZero() {
        // Given
        Long categoryId = 1L;
        ScenicCategory category = TestDataFactory.createScenicCategory(categoryId, "自然风光");

        given(scenicCategoryRepository.selectOne(any(QueryWrapper.class))).willReturn(category);
        given(scenicSpotRepository.selectCount(any(QueryWrapper.class))).willReturn(null);

        // When
        Integer result = scenicCategoryService.getSpotCountByCategoryId(categoryId);

        // Then
        assertThat(result).isZero();
    }
}
