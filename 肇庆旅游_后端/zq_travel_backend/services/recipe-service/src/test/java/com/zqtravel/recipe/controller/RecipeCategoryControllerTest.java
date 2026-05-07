package com.zqtravel.recipe.controller;

import com.zqtravel.recipe.dto.RecipeCategoryDTO;
import com.zqtravel.recipe.service.RecipeCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 食谱分类控制器单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@WebMvcTest(RecipeCategoryController.class)
class RecipeCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeCategoryService recipeCategoryService;

    @Test
    @DisplayName("getCategories - 存在分类时应返回列表")
    void getCategories_WithExistingCategories_ShouldReturnList() throws Exception {
        // Given
        RecipeCategoryDTO category1 = createTestCategoryDTO(1L, "中式菜肴", 1);
        RecipeCategoryDTO category2 = createTestCategoryDTO(2L, "西式料理", 2);

        when(recipeCategoryService.getAllCategories())
                .thenReturn(Arrays.asList(category1, category2));

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("中式菜肴"))
                .andExpect(jsonPath("$.data[1].name").value("西式料理"));

        verify(recipeCategoryService).getAllCategories();
    }

    @Test
    @DisplayName("getCategories - 无分类时应返回空列表")
    void getCategories_WithNoCategories_ShouldReturnEmptyList() throws Exception {
        // Given
        when(recipeCategoryService.getAllCategories())
                .thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/v1/recipes/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(recipeCategoryService).getAllCategories();
    }

    private RecipeCategoryDTO createTestCategoryDTO(Long id, String name, int sortOrder) {
        RecipeCategoryDTO dto = new RecipeCategoryDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setIcon("icon" + id + ".png");
        dto.setSortOrder(sortOrder);
        return dto;
    }
}
