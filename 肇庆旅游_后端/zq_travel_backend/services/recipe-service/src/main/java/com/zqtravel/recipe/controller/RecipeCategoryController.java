package com.zqtravel.recipe.controller;

import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.recipe.dto.RecipeCategoryDTO;
import com.zqtravel.recipe.service.RecipeCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 食谱分类控制器
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@RestController
@RequestMapping("/api/v1/recipes/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "食谱分类", description = "食谱分类相关接口")
public class RecipeCategoryController {

    private final RecipeCategoryService recipeCategoryService;

    @GetMapping
    @Operation(summary = "获取食谱分类", description = "获取所有食谱分类，按排序返回")
    public ApiResponse<List<RecipeCategoryDTO>> getCategories() {
        log.debug("查询食谱分类列表");
        
        List<RecipeCategoryDTO> categories = recipeCategoryService.getAllCategories();
        return ApiResponse.success(categories);
    }
}