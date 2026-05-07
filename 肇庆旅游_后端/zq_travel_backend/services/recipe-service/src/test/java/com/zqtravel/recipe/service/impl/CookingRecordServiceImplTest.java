package com.zqtravel.recipe.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.dto.CookingRecordDTO;
import com.zqtravel.recipe.dto.CookingRecordRequest;
import com.zqtravel.recipe.entity.CookingRecord;
import com.zqtravel.recipe.entity.Recipe;
import com.zqtravel.recipe.repository.CookingRecordRepository;
import com.zqtravel.recipe.repository.RecipeRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 烹饪记录服务实现类单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@ExtendWith(MockitoExtension.class)
class CookingRecordServiceImplTest {

    @Mock
    private CookingRecordRepository cookingRecordRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private CookingRecordServiceImpl cookingRecordService;

    private Recipe testRecipe;
    private CookingRecord testRecord;
    private static final Long TEST_RECIPE_ID = 1L;
    private static final Long TEST_USER_ID = 1001L;
    private static final Long TEST_RECORD_ID = 1L;

    @BeforeEach
    void setUp() {
        testRecipe = createTestRecipe();
        testRecord = createTestCookingRecord();
    }

    private Recipe createTestRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(TEST_RECIPE_ID);
        recipe.setName("肇庆裹蒸粽");
        recipe.setImageUrl("https://example.com/recipe1.jpg");
        recipe.setStatus(1);
        recipe.setIsDeleted(0);
        return recipe;
    }

    private CookingRecord createTestCookingRecord() {
        CookingRecord record = new CookingRecord();
        record.setId(TEST_RECORD_ID);
        record.setUserId(TEST_USER_ID);
        record.setRecipeId(TEST_RECIPE_ID);
        record.setRating(5);
        record.setNote("味道很好");
        return record;
    }

    @Test
    @DisplayName("recordCooking - 正常记录烹饪应返回记录ID")
    void recordCooking_WithValidRequest_ShouldReturnRecordId() {
        // Given
        CookingRecordRequest request = new CookingRecordRequest();
        request.setNote("第一次尝试");
        request.setRating(5);

        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);
        when(cookingRecordRepository.insert(any(CookingRecord.class))).thenAnswer(invocation -> {
            CookingRecord record = invocation.getArgument(0);
            record.setId(TEST_RECORD_ID);
            return 1;
        });

        // When
        Long result = cookingRecordService.recordCooking(TEST_RECIPE_ID, TEST_USER_ID, request);

        // Then
        assertThat(result).isEqualTo(TEST_RECORD_ID);
        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(cookingRecordRepository).insert(any(CookingRecord.class));
    }

    @Test
    @DisplayName("recordCooking - 食谱不存在时应抛出异常")
    void recordCooking_WithNonExistingRecipe_ShouldThrowException() {
        // Given
        CookingRecordRequest request = new CookingRecordRequest();
        request.setNote("测试");
        request.setRating(5);

        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> cookingRecordService.recordCooking(TEST_RECIPE_ID, TEST_USER_ID, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("食谱不存在");

        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(cookingRecordRepository, never()).insert(any());
    }

    @Test
    @DisplayName("recordCooking - 食谱已删除时应抛出异常")
    void recordCooking_WithDeletedRecipe_ShouldThrowException() {
        // Given
        CookingRecordRequest request = new CookingRecordRequest();
        request.setNote("测试");
        request.setRating(5);

        testRecipe.setIsDeleted(1);
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);

        // When & Then
        assertThatThrownBy(() -> cookingRecordService.recordCooking(TEST_RECIPE_ID, TEST_USER_ID, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("食谱不存在");

        verify(recipeRepository).selectById(TEST_RECIPE_ID);
        verify(cookingRecordRepository, never()).insert(any());
    }

    @Test
    @DisplayName("recordCooking - 备注为空时应成功记录")
    void recordCooking_WithNullNote_ShouldSucceed() {
        // Given
        CookingRecordRequest request = new CookingRecordRequest();
        request.setRating(4);

        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);
        when(cookingRecordRepository.insert(any(CookingRecord.class))).thenAnswer(invocation -> {
            CookingRecord record = invocation.getArgument(0);
            record.setId(TEST_RECORD_ID);
            return 1;
        });

        // When
        Long result = cookingRecordService.recordCooking(TEST_RECIPE_ID, TEST_USER_ID, request);

        // Then
        assertThat(result).isEqualTo(TEST_RECORD_ID);
        verify(cookingRecordRepository).insert(any(CookingRecord.class));
    }

    @Test
    @DisplayName("getCookingRecords - 正常查询应返回分页结果")
    void getCookingRecords_WithExistingRecords_ShouldReturnPage() {
        // Given
        Integer page = 1;
        Integer size = 20;
        Page<CookingRecord> recordPage = new Page<>(page, size);
        recordPage.setRecords(Collections.singletonList(testRecord));
        recordPage.setTotal(1);

        when(cookingRecordRepository.selectByUserId(any(Page.class), eq(TEST_USER_ID)))
                .thenReturn(recordPage);
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);

        // When
        IPage<CookingRecordDTO> result = cookingRecordService.getCookingRecords(TEST_USER_ID, page, size);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotal()).isEqualTo(1);
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0).getRecipeName()).isEqualTo("肇庆裹蒸粽");

        verify(cookingRecordRepository).selectByUserId(any(Page.class), eq(TEST_USER_ID));
    }

    @Test
    @DisplayName("getCookingRecords - 无记录时应返回空分页")
    void getCookingRecords_WithNoRecords_ShouldReturnEmptyPage() {
        // Given
        Integer page = 1;
        Integer size = 20;
        Page<CookingRecord> recordPage = new Page<>(page, size);
        recordPage.setRecords(Collections.emptyList());
        recordPage.setTotal(0);

        when(cookingRecordRepository.selectByUserId(any(Page.class), eq(TEST_USER_ID)))
                .thenReturn(recordPage);

        // When
        IPage<CookingRecordDTO> result = cookingRecordService.getCookingRecords(TEST_USER_ID, page, size);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotal()).isEqualTo(0);
        assertThat(result.getRecords()).isEmpty();
    }

    @Test
    @DisplayName("getCookingRecord - 存在的记录应返回记录详情")
    void getCookingRecord_WithExistingRecord_ShouldReturnRecord() {
        // Given
        List<CookingRecord> records = Collections.singletonList(testRecord);
        when(cookingRecordRepository.selectByUserAndRecipe(TEST_USER_ID, TEST_RECIPE_ID))
                .thenReturn(records);
        when(recipeRepository.selectById(TEST_RECIPE_ID)).thenReturn(testRecipe);

        // When
        CookingRecordDTO result = cookingRecordService.getCookingRecord(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_RECORD_ID);
        assertThat(result.getRecipeName()).isEqualTo("肇庆裹蒸粽");
        assertThat(result.getRating()).isEqualTo(5);
    }

    @Test
    @DisplayName("getCookingRecord - 不存在的记录应返回null")
    void getCookingRecord_WithNoRecord_ShouldReturnNull() {
        // Given
        when(cookingRecordRepository.selectByUserAndRecipe(TEST_USER_ID, TEST_RECIPE_ID))
                .thenReturn(Collections.emptyList());

        // When
        CookingRecordDTO result = cookingRecordService.getCookingRecord(TEST_RECIPE_ID, TEST_USER_ID);

        // Then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("countCookingRecords - 多条记录应返回正确数量")
    void countCookingRecords_WithMultipleRecords_ShouldReturnCount() {
        // Given
        int expectedCount = 5;
        when(cookingRecordRepository.countByUserId(TEST_USER_ID)).thenReturn(expectedCount);

        // When
        int result = cookingRecordService.countCookingRecords(TEST_USER_ID);

        // Then
        assertThat(result).isEqualTo(expectedCount);
        verify(cookingRecordRepository).countByUserId(TEST_USER_ID);
    }

    @Test
    @DisplayName("countCookingRecords - 无记录时应返回0")
    void countCookingRecords_WithNoRecords_ShouldReturnZero() {
        // Given
        when(cookingRecordRepository.countByUserId(TEST_USER_ID)).thenReturn(0);

        // When
        int result = cookingRecordService.countCookingRecords(TEST_USER_ID);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("getRecentRecipeIds - 正常查询应返回食谱ID列表")
    void getRecentRecipeIds_WithValidLimit_ShouldReturnList() {
        // Given
        int limit = 5;
        List<Long> recipeIds = Arrays.asList(1L, 2L, 3L);
        when(cookingRecordRepository.selectRecentRecipeIds(TEST_USER_ID, limit))
                .thenReturn(recipeIds);

        // When
        List<Long> result = cookingRecordService.getRecentRecipeIds(TEST_USER_ID, limit);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("getRecentRecipeIds - 限制数量大于记录数时应返回所有")
    void getRecentRecipeIds_WithLimitGreaterThanRecords_ShouldReturnAll() {
        // Given
        int limit = 10;
        List<Long> recipeIds = Arrays.asList(1L, 2L);
        when(cookingRecordRepository.selectRecentRecipeIds(TEST_USER_ID, limit))
                .thenReturn(recipeIds);

        // When
        List<Long> result = cookingRecordService.getRecentRecipeIds(TEST_USER_ID, limit);

        // Then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("getRecentRecipeIds - 无记录时应返回空列表")
    void getRecentRecipeIds_WithNoRecords_ShouldReturnEmptyList() {
        // Given
        int limit = 5;
        when(cookingRecordRepository.selectRecentRecipeIds(TEST_USER_ID, limit))
                .thenReturn(Collections.emptyList());

        // When
        List<Long> result = cookingRecordService.getRecentRecipeIds(TEST_USER_ID, limit);

        // Then
        assertThat(result).isEmpty();
    }
}
