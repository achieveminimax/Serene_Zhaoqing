package com.zqtravel.recipe.service.impl;

import com.zqtravel.recipe.dto.RecipeCategoryDTO;
import com.zqtravel.recipe.entity.RecipeCategory;
import com.zqtravel.recipe.repository.RecipeCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * 食谱分类服务实现类单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@ExtendWith(MockitoExtension.class)
class RecipeCategoryServiceImplTest {

    @Mock
    private RecipeCategoryRepository recipeCategoryRepository;

    @InjectMocks
    private RecipeCategoryServiceImpl recipeCategoryService;

    private RecipeCategory testCategory1;
    private RecipeCategory testCategory2;
    private static final Long TEST_CATEGORY_ID = 1L;

    @BeforeEach
    void setUp() {
        testCategory1 = createTestCategory(1L, "中式菜肴", 1);
        testCategory2 = createTestCategory(2L, "西式料理", 2);
    }

    private RecipeCategory createTestCategory(Long id, String name, int sortOrder) {
        RecipeCategory category = new RecipeCategory();
        category.setId(id);
        category.setName(name);
        category.setIcon("icon" + id + ".png");
        category.setSortOrder(sortOrder);
        return category;
    }

    @Test
    @DisplayName("getAllCategories - 存在分类时应返回排序后的列表")
    void getAllCategories_WithExistingCategories_ShouldReturnList() {
        // Given
        List<RecipeCategory> categories = Arrays.asList(testCategory1, testCategory2);
        when(recipeCategoryRepository.selectAllOrderBySort()).thenReturn(categories);

        // When
        List<RecipeCategoryDTO> result = recipeCategoryService.getAllCategories();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("中式菜肴");
        assertThat(result.get(1).getName()).isEqualTo("西式料理");
        verify(recipeCategoryRepository).selectAllOrderBySort();
    }

    @Test
    @DisplayName("getAllCategories - 无分类时应返回空列表")
    void getAllCategories_WithNoCategories_ShouldReturnEmptyList() {
        // Given
        when(recipeCategoryRepository.selectAllOrderBySort()).thenReturn(Collections.emptyList());

        // When
        List<RecipeCategoryDTO> result = recipeCategoryService.getAllCategories();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(recipeCategoryRepository).selectAllOrderBySort();
    }

    @Test
    @DisplayName("getCategoryById - 存在的分类应返回分类信息")
    void getCategoryById_WithExistingCategory_ShouldReturnCategory() {
        // Given
        when(recipeCategoryRepository.selectById(TEST_CATEGORY_ID)).thenReturn(testCategory1);

        // When
        RecipeCategoryDTO result = recipeCategoryService.getCategoryById(TEST_CATEGORY_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_CATEGORY_ID);
        assertThat(result.getName()).isEqualTo("中式菜肴");
        assertThat(result.getSortOrder()).isEqualTo(1);
        verify(recipeCategoryRepository).selectById(TEST_CATEGORY_ID);
    }

    @Test
    @DisplayName("getCategoryById - 不存在的分类应返回null")
    void getCategoryById_WithNonExistingCategory_ShouldReturnNull() {
        // Given
        when(recipeCategoryRepository.selectById(TEST_CATEGORY_ID)).thenReturn(null);

        // When
        RecipeCategoryDTO result = recipeCategoryService.getCategoryById(TEST_CATEGORY_ID);

        // Then
        assertThat(result).isNull();
        verify(recipeCategoryRepository).selectById(TEST_CATEGORY_ID);
    }

    @Test
    @DisplayName("getCategoryByName - 存在的名称应返回分类")
    void getCategoryByName_WithExistingName_ShouldReturnCategory() {
        // Given
        String categoryName = "中式菜肴";
        when(recipeCategoryRepository.selectList(null)).thenReturn(Arrays.asList(testCategory1, testCategory2));

        // When
        RecipeCategoryDTO result = recipeCategoryService.getCategoryByName(categoryName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(categoryName);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getCategoryByName - 不存在的名称应返回null")
    void getCategoryByName_WithNonExistingName_ShouldReturnNull() {
        // Given
        String categoryName = "不存在分类";
        when(recipeCategoryRepository.selectList(null)).thenReturn(Arrays.asList(testCategory1, testCategory2));

        // When
        RecipeCategoryDTO result = recipeCategoryService.getCategoryByName(categoryName);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getCategoryByName - 空列表时应返回null")
    void getCategoryByName_WithEmptyList_ShouldReturnNull() {
        // Given
        String categoryName = "中式菜肴";
        when(recipeCategoryRepository.selectList(null)).thenReturn(Collections.emptyList());

        // When
        RecipeCategoryDTO result = recipeCategoryService.getCategoryByName(categoryName);

        // Then
        assertThat(result).isNull();
    }
}
