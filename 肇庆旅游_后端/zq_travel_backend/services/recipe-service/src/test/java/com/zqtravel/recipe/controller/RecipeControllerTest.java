package com.zqtravel.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.recipe.dto.*;
import com.zqtravel.recipe.service.CookingRecordService;
import com.zqtravel.recipe.service.RecipeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 食谱控制器单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private CookingRecordService cookingRecordService;

    private static final Long TEST_USER_ID = 1001L;
    private static final Long TEST_RECIPE_ID = 1L;

    @Test
    @DisplayName("getRecipes - 正常请求应返回食谱列表")
    void getRecipes_ShouldReturnSuccess() throws Exception {
        // Given
        PageResult<RecipeDTO> pageResult = PageResult.<RecipeDTO>builder()
                .page(1)
                .size(20)
                .total(1L)
                .items(Collections.singletonList(createTestRecipeDTO()))
                .build();

        com.baomidou.mybatisplus.extension.plugins.pagination.Page<RecipeDTO> page = 
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 20, 1);
        page.setRecords(Collections.singletonList(createTestRecipeDTO()));
        when(recipeService.getRecipes(anyInt(), anyInt(), isNull(), isNull(), eq(TEST_USER_ID)))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/recipes")
                        .header("X-User-Id", TEST_USER_ID)
                        .param("page", "1")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.items[0].name").value("肇庆裹蒸粽"));
    }

    @Test
    @DisplayName("getRecipe - 存在的食谱应返回详情")
    void getRecipe_WithExistingId_ShouldReturnRecipe() throws Exception {
        // Given
        RecipeDTO recipeDTO = createTestRecipeDTO();
        when(recipeService.getRecipeById(TEST_RECIPE_ID, TEST_USER_ID)).thenReturn(recipeDTO);

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/{id}", TEST_RECIPE_ID)
                        .header("X-User-Id", TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.id").value(TEST_RECIPE_ID))
                .andExpect(jsonPath("$.data.name").value("肇庆裹蒸粽"));
    }

    @Test
    @DisplayName("getRecipe - 不存在的食谱应返回404")
    void getRecipe_WithNonExistingId_ShouldReturnNotFound() throws Exception {
        // Given
        when(recipeService.getRecipeById(TEST_RECIPE_ID, TEST_USER_ID)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/{id}", TEST_RECIPE_ID)
                        .header("X-User-Id", TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("食谱不存在"));
    }

    @Test
    @DisplayName("favoriteRecipe - 添加收藏应成功")
    void favoriteRecipe_WithAddAction_ShouldReturnSuccess() throws Exception {
        // Given
        FavoriteRequest request = new FavoriteRequest();
        request.setAction("add");

        when(recipeService.favoriteRecipe(eq(TEST_RECIPE_ID), eq(TEST_USER_ID), any(FavoriteRequest.class)))
                .thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/v1/recipes/{id}/favorite", TEST_RECIPE_ID)
                        .header("X-User-Id", TEST_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @DisplayName("favoriteRecipe - 取消收藏应成功")
    void favoriteRecipe_WithRemoveAction_ShouldReturnSuccess() throws Exception {
        // Given
        FavoriteRequest request = new FavoriteRequest();
        request.setAction("remove");

        when(recipeService.favoriteRecipe(eq(TEST_RECIPE_ID), eq(TEST_USER_ID), any(FavoriteRequest.class)))
                .thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/v1/recipes/{id}/favorite", TEST_RECIPE_ID)
                        .header("X-User-Id", TEST_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @DisplayName("getFavoriteRecipes - 应返回收藏列表")
    void getFavoriteRecipes_ShouldReturnList() throws Exception {
        // Given
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<RecipeDTO> emptyPage = 
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 20, 0);
        when(recipeService.getFavoriteRecipes(eq(TEST_USER_ID), anyInt(), anyInt()))
                .thenReturn(emptyPage);

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/favorites")
                        .header("X-User-Id", TEST_USER_ID)
                        .param("page", "1")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.items").isArray());
    }

    @Test
    @DisplayName("recordCooking - 正常记录应返回记录ID")
    void recordCooking_WithValidRequest_ShouldReturnRecordId() throws Exception {
        // Given
        CookingRecordRequest request = new CookingRecordRequest();
        request.setNote("味道很好");
        request.setRating(5);

        when(cookingRecordService.recordCooking(eq(TEST_RECIPE_ID), eq(TEST_USER_ID), any(CookingRecordRequest.class)))
                .thenReturn(123L);

        // When & Then
        mockMvc.perform(post("/api/v1/recipes/{id}/cooking-record", TEST_RECIPE_ID)
                        .header("X-User-Id", TEST_USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").value(123));
    }

    @Test
    @DisplayName("getDailyRecommend - 应返回推荐列表")
    void getDailyRecommend_ShouldReturnList() throws Exception {
        // Given
        List<RecipeDTO> recipes = Collections.singletonList(createTestRecipeDTO());
        when(recipeService.getPopularRecipes(anyInt(), eq(TEST_USER_ID))).thenReturn(recipes);

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/daily-recommend")
                        .header("X-User-Id", TEST_USER_ID)
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("肇庆裹蒸粽"));
    }

    private RecipeDTO createTestRecipeDTO() {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(TEST_RECIPE_ID);
        dto.setName("肇庆裹蒸粽");
        dto.setCategoryId(1L);
        dto.setDifficulty("中等");
        dto.setCookTime(60);
        dto.setCalories(350);
        dto.setViewCount(100);
        dto.setFavoriteCount(50);
        dto.setIsFavorite(false);
        return dto;
    }
}
