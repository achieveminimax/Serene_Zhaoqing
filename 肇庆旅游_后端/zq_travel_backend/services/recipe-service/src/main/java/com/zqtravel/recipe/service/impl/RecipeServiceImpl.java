package com.zqtravel.recipe.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.dto.RecipeDTO;
import com.zqtravel.recipe.dto.FavoriteRequest;
import com.zqtravel.recipe.entity.Recipe;
import com.zqtravel.recipe.repository.RecipeRepository;
import com.zqtravel.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 食谱服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    // 注意：收藏功能需要使用user_favorites表，这里暂时简化实现

    @Override
    public IPage<RecipeDTO> getRecipes(Integer page, Integer size, Long categoryId, String difficulty, Long userId) {
        Page<Recipe> pageParam = new Page<>(page, size);
        IPage<Recipe> recipePage = recipeRepository.selectRecipePage(pageParam, categoryId, difficulty, 1);
        
        return recipePage.convert(recipe -> {
            RecipeDTO dto = convertToDTO(recipe);
            // 设置是否收藏（需要查询收藏表，这里暂时简化）
            dto.setIsFavorite(isFavorite(recipe.getId(), userId));
            return dto;
        });
    }

    @Override
    public RecipeDTO getRecipeById(Long id, Long userId) {
        Recipe recipe = recipeRepository.selectById(id);
        if (recipe == null || recipe.getIsDeleted() == 1) {
            return null;
        }
        
        // 增加浏览次数
        incrementViewCount(id);
        
        RecipeDTO dto = convertToDTO(recipe);
        dto.setIsFavorite(isFavorite(id, userId));
        return dto;
    }

    @Override
    public List<RecipeDTO> getPopularRecipes(int limit, Long userId) {
        List<Recipe> recipes = recipeRepository.selectPopularRecipes(limit);
        return recipes.stream()
                .map(recipe -> {
                    RecipeDTO dto = convertToDTO(recipe);
                    dto.setIsFavorite(isFavorite(recipe.getId(), userId));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean favoriteRecipe(Long recipeId, Long userId, FavoriteRequest request) {
        // 这里需要实现收藏逻辑，使用user_favorites表
        // 暂时简化实现：只更新食谱的收藏次数
        if ("add".equals(request.getAction())) {
            recipeRepository.updateFavoriteCount(recipeId, 1);
            log.info("用户{}收藏了食谱{}", userId, recipeId);
            return true;
        } else if ("remove".equals(request.getAction())) {
            recipeRepository.updateFavoriteCount(recipeId, -1);
            log.info("用户{}取消收藏了食谱{}", userId, recipeId);
            return true;
        }
        return false;
    }

    @Override
    public IPage<RecipeDTO> getFavoriteRecipes(Long userId, Integer page, Integer size) {
        // 这里需要查询user_favorites表获取用户收藏的食谱ID
        // 暂时返回空分页
        Page<RecipeDTO> resultPage = new Page<>(page, size);
        resultPage.setTotal(0);
        return resultPage;
    }

    @Override
    public boolean isFavorite(Long recipeId, Long userId) {
        // 这里需要查询user_favorites表
        // 暂时返回false
        return false;
    }

    @Override
    public void incrementViewCount(Long id) {
        recipeRepository.incrementViewCount(id);
    }

    /**
     * 将Recipe实体转换为RecipeDTO
     */
    private RecipeDTO convertToDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        BeanUtils.copyProperties(recipe, dto);
        
        // 转换内部对象
        if (recipe.getIngredients() != null) {
            dto.setIngredients(recipe.getIngredients().stream()
                    .map(ingredient -> {
                        RecipeDTO.IngredientDTO ingredientDTO = new RecipeDTO.IngredientDTO();
                        BeanUtils.copyProperties(ingredient, ingredientDTO);
                        return ingredientDTO;
                    })
                    .collect(Collectors.toList()));
        }
        
        if (recipe.getSteps() != null) {
            dto.setSteps(recipe.getSteps().stream()
                    .map(step -> {
                        RecipeDTO.StepDTO stepDTO = new RecipeDTO.StepDTO();
                        BeanUtils.copyProperties(step, stepDTO);
                        return stepDTO;
                    })
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}