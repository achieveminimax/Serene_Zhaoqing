package com.zqtravel.recipe.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.dto.FavoriteRequest;
import com.zqtravel.recipe.dto.RecipeDTO;
import com.zqtravel.recipe.entity.Recipe;
import com.zqtravel.recipe.repository.RecipeRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 食谱服务实现类单元测试
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe testRecipe;
    private static final Long TEST_RECIPE_ID = 1L;
    private static final Long TEST_USER_ID = 1001L;
    private static final Long TEST_CATEGORY_ID = 1L;

    @BeforeEach
    void setUp() {
        testRecipe = createTestRecipe();
    }

    private Recipe createTestRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(TEST_RECIPE_ID);
        recipe.setName("肇庆裹蒸粽");
        recipe.setCategoryId(TEST_CATEGORY_ID);
        recipe.setDifficulty("中等");
        recipe.setStatus(1);
        recipe.setIsDeleted(0);
        recipe.setViewCount(100);
        recipe.setFavoriteCount(50);
        recipe.setCalories(350);
        recipe.setCookTime(60);
        recipe.setIngredients(new ArrayList<>());
        recipe.setSteps(new ArrayList<>());
        return recipe;
    }

    @Test
    @DisplayName("getRecipes - 正常分页查询应返回分页结果")
    void getRecipes_WithValidParams_ShouldReturnPage() {
        // Given
        Integer page = 1;
        Integer size = 20;
        Page<Recipe> recipePage = new Page<>(page, size);
        recipePage.setRecords(Collections.singletonList(testRecipe));
        recipePage.setTotal(1);

        when(recipeRepository.selectRecipePage(any(Page.class), isNull(), isNull(), eq(1)))
                .thenReturn(recipePage);

        // When
        IPage<RecipeDTO> result = recipeService.getRecipes(page, size, null, null, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCurrent()).isEqualTo(page.longValue());
        assertThat(result.getSize()).isEqualTo(size.longValue());
        assertThat(result.getTotal()).isEqualTo(1L);
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0).getName()).isEqualTo("肇庆裹蒸粽");
        
        verify(recipeRepository).selectRecipePage(any(Page.class), isNull(), isNull(), eq(1));
    }

    @Test
    @DisplayName("getRecipes - 带分类筛选应返回筛选后的结果")
    void getRecipes_WithCategoryFilter_ShouldFilterByCategory() {
        // Given
        Integer page = 1;
        Integer size = 20;
        Page<Recipe> recipePage = new Page<>(page, size);
        recipePage.setRecords(Collections.singletonList(testRecipe));
        recipePage.setTotal(1);

        when(recipeRepository.selectRecipePage(any(Page.class), eq(TEST_CATEGORY_ID), isNull(), eq(1)))
                .thenReturn(recipePage);

        // When
        IPage<RecipeDTO> result = recipeService.getRecipes(page, size, TEST_CATEGORY_ID, null, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        verify(recipeRepository).selectRecipePage(any(Page.class), eq(TEST_CATEGORY_ID), isNull(), eq(1));
    }

    @Test
    @DisplayName("getRecipes - 带难度筛选应返回筛选后的结果")
    void getRecipes_WithDifficultyFilter_ShouldFilterByDifficulty() {
        // Given
        Integer page = 1;
        Integer size = 20;
        String difficulty = "中等";
        Page<Recipe> recipePage = new Page<>(page, size);
        recipePage.setRecords(Collections.singletonList(testRecipe));
        recipePage.setTotal(1);

        when(recipeRepository.selectRecipePage(any(Page.class), isNull(), eq(difficulty), eq(1)))
                .thenReturn(recipePage);

        // When
        IPage<RecipeDTO> result = recipeService.getRecipes(page, size, null, difficulty, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        verify(recipeRepository).selectRecipePage(any(Page.class), isNull(), eq(difficulty), eq(1));
    }

    @Test
    @DisplayName("getRecipes - 无结果时应返回空分页")
    void getRecipes_WithNoResults_ShouldReturnEmptyPage() {
        // Given
        Integer page = 1;
        Integer size = 20;
        Page<Recipe> recipePage = new Page<>(page, size);
        recipePage.setRecords(Collections.emptyList());
        recipePage.setTotal(0);

        when(recipeRepository.selectRecipePage(any(Page.class), isNull(), isNull(), eq(1)))
                .thenReturn(recipePage);

        // When
        IPage<RecipeDTO> result = recipeService.getRecipes(page, size, null, null, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).isEmpty();
        assertThat(result.getTotal()).isEqualTo(0);
    }

    @Test
    @DisplayName("getRecipeById - 存在的食谱应返回详情")
    void getRecipeById_WithExistingRecipe_ShouldReturnRecipe() {
        // Given
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);
        when(recipeRepository.incrementViewCount(TEST_RECIPE_ID)).thenReturn(1);

        // When
        RecipeDTO result = recipeService.getRecipeById(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_RECIPE_ID);
        assertThat(result.getName()).isEqualTo("肇庆裹蒸粽");
        assertThat(result.getDifficulty()).isEqualTo("中等");
        
        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(recipeRepository).incrementViewCount(TEST_RECIPE_ID);
    }

    @Test
    @DisplayName("getRecipeById - 不存在的食谱应返回null")
    void getRecipeById_WithNonExistingRecipe_ShouldReturnNull() {
        // Given
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(null);

        // When
        RecipeDTO result = recipeService.getRecipeById(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isNull();
        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(recipeRepository, never()).incrementViewCount(any());
    }

    @Test
    @DisplayName("getRecipeById - 已删除的食谱应返回null")
    void getRecipeById_WithDeletedRecipe_ShouldReturnNull() {
        // Given
        testRecipe.setIsDeleted(1);
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);

        // When
        RecipeDTO result = recipeService.getRecipeById(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isNull();
        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(recipeRepository, never()).incrementViewCount(any());
    }

    @Test
    @DisplayName("getPopularRecipes - 正常查询应返回热门食谱列表")
    void getPopularRecipes_WithValidLimit_ShouldReturnList() {
        // Given
        int limit = 10;
        List<Recipe> recipes = Collections.singletonList(testRecipe);
        when(recipeRepository.selectPopularRecipes(limit)).thenReturn(recipes);

        // When
        List<RecipeDTO> result = recipeService.getPopularRecipes(limit, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("肇庆裹蒸粽");
        verify(recipeRepository).selectPopularRecipes(limit);
    }

    @Test
    @DisplayName("getPopularRecipes - 零限制应返回空列表")
    void getPopularRecipes_WithZeroLimit_ShouldReturnEmptyList() {
        // Given
        int limit = 0;
        when(recipeRepository.selectPopularRecipes(limit)).thenReturn(Collections.emptyList());

        // When
        List<RecipeDTO> result = recipeService.getPopularRecipes(limit, TEST_USER_ID);

        // Then
        assertThat(result).isEmpty();
        verify(recipeRepository).selectPopularRecipes(limit);
    }

    @Test
    @DisplayName("favoriteRecipe - 添加收藏应增加收藏计数")
    void favoriteRecipe_WithAddAction_ShouldIncrementCount() {
        // Given
        FavoriteRequest request = new FavoriteRequest();
        request.setAction("add");
        when(recipeRepository.updateFavoriteCount(TEST_RECIPE_ID, 1)).thenReturn(1);

        // When
        boolean result = recipeService.favoriteRecipe(TEST_RECIPE_ID, TEST_USER_ID, request);

        // Then
        assertThat(result).isTrue();
        verify(recipeRepository).updateFavoriteCount(TEST_RECIPE_ID, 1);
    }

    @Test
    @DisplayName("favoriteRecipe - 取消收藏应减少收藏计数")
    void favoriteRecipe_WithRemoveAction_ShouldDecrementCount() {
        // Given
        FavoriteRequest request = new FavoriteRequest();
        request.setAction("remove");
        when(recipeRepository.updateFavoriteCount(TEST_RECIPE_ID, -1)).thenReturn(1);

        // When
        boolean result = recipeService.favoriteRecipe(TEST_RECIPE_ID, TEST_USER_ID, request);

        // Then
        assertThat(result).isTrue();
        verify(recipeRepository).updateFavoriteCount(TEST_RECIPE_ID, -1);
    }

    @Test
    @DisplayName("favoriteRecipe - 无效操作类型应返回false")
    void favoriteRecipe_WithInvalidAction_ShouldReturnFalse() {
        // Given
        FavoriteRequest request = new FavoriteRequest();
        request.setAction("invalid");

        // When
        boolean result = recipeService.favoriteRecipe(TEST_RECIPE_ID, TEST_USER_ID, request);

        // Then
        assertThat(result).isFalse();
        verify(recipeRepository, never()).updateFavoriteCount(any(), anyInt());
    }

    @Test
    @DisplayName("getFavoriteRecipes - 应返回空分页（简化实现）")
    void getFavoriteRecipes_ShouldReturnEmptyPage() {
        // Given
        Integer page = 1;
        Integer size = 20;

        // When
        IPage<RecipeDTO> result = recipeService.getFavoriteRecipes(TEST_USER_ID, page, size);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotal()).isEqualTo(0);
        assertThat(result.getRecords()).isEmpty();
    }

    @Test
    @DisplayName("isFavorite - 应返回false（简化实现）")
    void isFavorite_ShouldReturnFalse() {
        // When
        boolean result = recipeService.isFavorite(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("isFavorite - 用户ID为null时应返回false")
    void isFavorite_WithNullUserId_ShouldReturnFalse() {
        // When
        boolean result = recipeService.isFavorite(TEST_RECIPE_ID, null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("incrementViewCount - 应调用repository方法")
    void incrementViewCount_ShouldCallRepository() {
        // Given
        when(recipeRepository.incrementViewCount(TEST_RECIPE_ID)).thenReturn(1);

        // When
        recipeService.incrementViewCount(TEST_RECIPE_ID);

        // Then
        verify(recipeRepository).incrementViewCount(TEST_RECIPE_ID);
    }
}
