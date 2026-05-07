package com.zqtravel.recipe.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.dto.CookingRecordDTO;
import com.zqtravel.recipe.dto.CookingRecordRequest;
import com.zqtravel.recipe.entity.CookingRecord;
import com.zqtravel.recipe.entity.Recipe;
import com.zqtravel.recipe.repository.CookingRecordRepository;
import com.zqtravel.recipe.repository.RecipeRepository;
import com.zqtravel.recipe.service.CookingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 烹饪记录服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CookingRecordServiceImpl implements CookingRecordService {

    private final CookingRecordRepository cookingRecordRepository;
    private final RecipeRepository recipeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long recordCooking(Long recipeId, Long userId, CookingRecordRequest request) {
        // 检查食谱是否存在
        Recipe recipe = recipeRepository.selectById(recipeId);
        if (recipe == null || recipe.getIsDeleted() == 1) {
            throw new IllegalArgumentException("食谱不存在");
        }

        CookingRecord record = new CookingRecord();
        record.setUserId(userId);
        record.setRecipeId(recipeId);
        record.setCookedAt(LocalDateTime.now());
        record.setNote(request.getNote());
        record.setRating(request.getRating());

        cookingRecordRepository.insert(record);
        log.info("用户{}记录了食谱{}的烹饪完成", userId, recipeId);
        
        return record.getId();
    }

    @Override
    public IPage<CookingRecordDTO> getCookingRecords(Long userId, Integer page, Integer size) {
        Page<CookingRecord> pageParam = new Page<>(page, size);
        IPage<CookingRecord> recordPage = cookingRecordRepository.selectByUserId(pageParam, userId);
        
        return recordPage.convert(this::convertToDTO);
    }

    @Override
    public CookingRecordDTO getCookingRecord(Long recipeId, Long userId) {
        List<CookingRecord> records = cookingRecordRepository.selectByUserAndRecipe(userId, recipeId);
        if (records.isEmpty()) {
            return null;
        }
        // 返回最近的一条记录
        return convertToDTO(records.get(0));
    }

    @Override
    public int countCookingRecords(Long userId) {
        return cookingRecordRepository.countByUserId(userId);
    }

    @Override
    public List<Long> getRecentRecipeIds(Long userId, int limit) {
        return cookingRecordRepository.selectRecentRecipeIds(userId, limit);
    }

    /**
     * 将CookingRecord实体转换为CookingRecordDTO
     */
    private CookingRecordDTO convertToDTO(CookingRecord record) {
        CookingRecordDTO dto = new CookingRecordDTO();
        BeanUtils.copyProperties(record, dto);
        
        // 查询食谱信息
        Recipe recipe = recipeRepository.selectById(record.getRecipeId());
        if (recipe != null) {
            dto.setRecipeName(recipe.getName());
            dto.setRecipeImage(recipe.getImageUrl());
        }
        
        return dto;
    }
}