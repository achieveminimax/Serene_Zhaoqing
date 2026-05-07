package com.zqtravel.recipe.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.recipe.dto.CookingRecordRequest;
import com.zqtravel.recipe.dto.FavoriteRequest;
import com.zqtravel.recipe.dto.PageResult;
import com.zqtravel.recipe.dto.RecipeDTO;
import com.zqtravel.recipe.service.CookingRecordService;
import com.zqtravel.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 食谱控制器
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "食谱管理", description = "食谱相关接口")
public class RecipeController {

    private final RecipeService recipeService;
    private final CookingRecordService cookingRecordService;

    @GetMapping
    @Operation(summary = "获取食谱列表", description = "分页查询食谱列表，支持按分类和难度筛选")
    public ApiResponse<PageResult<RecipeDTO>> getRecipes(
            @Parameter(description = "页码", example = "1") 
            @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", example = "20") 
            @RequestParam(defaultValue = "20") Integer size,
            @Parameter(description = "分类ID") 
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "难度") 
            @RequestParam(required = false) String difficulty,
            @Parameter(description = "用户ID（从网关传递）") 
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        
        log.debug("查询食谱列表，页码: {}, 大小: {}, 分类ID: {}, 难度: {}, 用户ID: {}", 
                page, size, categoryId, difficulty, userId);
        
        IPage<RecipeDTO> recipePage = recipeService.getRecipes(page, size, categoryId, difficulty, userId);
        return ApiResponse.success(PageResult.of(recipePage));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取食谱详情", description = "根据ID获取食谱详情，同时增加浏览次数")
    public ApiResponse<RecipeDTO> getRecipe(
            @Parameter(description = "食谱ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "用户ID（从网关传递）")
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        
        log.debug("查询食谱详情，食谱ID: {}, 用户ID: {}", id, userId);
        
        RecipeDTO recipe = recipeService.getRecipeById(id, userId);
        if (recipe == null) {
            return ApiResponse.<RecipeDTO>error("404", "食谱不存在", null);
        }
        return ApiResponse.success(recipe);
    }

    @PostMapping("/{id}/favorite")
    @Operation(summary = "收藏/取消收藏食谱", description = "收藏或取消收藏指定的食谱")
    public ApiResponse<Boolean> favoriteRecipe(
            @Parameter(description = "食谱ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "用户ID（从网关传递）", required = true)
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody FavoriteRequest request) {
        
        log.debug("收藏操作，食谱ID: {}, 用户ID: {}, 操作: {}", id, userId, request.getAction());
        
        boolean success = recipeService.favoriteRecipe(id, userId, request);
        return ApiResponse.success(success);
    }

    @GetMapping("/favorites")
    @Operation(summary = "获取收藏食谱", description = "获取用户收藏的食谱列表")
    public ApiResponse<PageResult<RecipeDTO>> getFavoriteRecipes(
            @Parameter(description = "页码", example = "1")
            @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", example = "20")
            @RequestParam(defaultValue = "20") Integer size,
            @Parameter(description = "用户ID（从网关传递）", required = true)
            @RequestHeader("X-User-Id") Long userId) {
        
        log.debug("查询收藏食谱，用户ID: {}, 页码: {}, 大小: {}", userId, page, size);
        
        IPage<RecipeDTO> favoritePage = recipeService.getFavoriteRecipes(userId, page, size);
        return ApiResponse.success(PageResult.of(favoritePage));
    }

    @PostMapping("/{id}/cooking-record")
    @Operation(summary = "记录烹饪完成", description = "记录用户完成了指定食谱的烹饪")
    public ApiResponse<Long> recordCooking(
            @Parameter(description = "食谱ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "用户ID（从网关传递）", required = true)
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CookingRecordRequest request) {
        
        log.debug("记录烹饪完成，食谱ID: {}, 用户ID: {}", id, userId);
        
        Long recordId = cookingRecordService.recordCooking(id, userId, request);
        return ApiResponse.success(recordId);
    }

    @GetMapping("/daily-recommend")
    @Operation(summary = "获取每日推荐食谱", description = "获取每日推荐的食谱列表")
    public ApiResponse<List<RecipeDTO>> getDailyRecommend(
            @Parameter(description = "用户ID（从网关传递）")
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @Parameter(description = "推荐数量", example = "10")
            @RequestParam(defaultValue = "10") Integer limit) {
        
        log.debug("获取每日推荐，用户ID: {}, 限制数量: {}", userId, limit);
        
        // 暂时使用热门食谱作为推荐
        List<RecipeDTO> recommends = recipeService.getPopularRecipes(limit, userId);
        return ApiResponse.success(recommends);
    }
}