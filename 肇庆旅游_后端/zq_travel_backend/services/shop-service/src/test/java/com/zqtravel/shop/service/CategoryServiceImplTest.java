package com.zqtravel.shop.service;

import com.zqtravel.shop.builder.CategoryTestDataBuilder;
import com.zqtravel.shop.dto.response.CategoryDTO;
import com.zqtravel.shop.entity.ProductCategory;
import com.zqtravel.shop.mapper.CategoryMapper;
import com.zqtravel.shop.service.impl.CategoryServiceImpl;
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
 * CategoryServiceImpl 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("分类服务测试")
class CategoryServiceImplTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("获取所有分类 - 成功")
    void listAllCategories_Success() {
        // Given
        List<ProductCategory> categories = Arrays.asList(
                CategoryTestDataBuilder.defaultCategory(),
                CategoryTestDataBuilder.childCategory()
        );
        when(categoryMapper.selectAllCategories()).thenReturn(categories);

        // When
        List<CategoryDTO> result = categoryService.listAllCategories();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("肇庆特产");
        assertThat(result.get(1).getName()).isEqualTo("糕点");
        verify(categoryMapper).selectAllCategories();
    }

    @Test
    @DisplayName("获取所有分类 - 空列表")
    void listAllCategories_Empty() {
        // Given
        when(categoryMapper.selectAllCategories()).thenReturn(Collections.emptyList());

        // When
        List<CategoryDTO> result = categoryService.listAllCategories();

        // Then
        assertThat(result).isEmpty();
        verify(categoryMapper).selectAllCategories();
    }

    @Test
    @DisplayName("获取分类树 - 成功")
    void getCategoryTree_Success() {
        // Given
        List<ProductCategory> categories = CategoryTestDataBuilder.categoryTree();
        when(categoryMapper.selectCategoryTree()).thenReturn(categories);

        // When
        List<CategoryDTO> result = categoryService.getCategoryTree();

        // Then
        assertThat(result).hasSize(2); // 两个一级分类
        assertThat(result.get(0).getName()).isEqualTo("肇庆特产");
        assertThat(result.get(0).getChildren()).hasSize(2); // 两个子分类
        assertThat(result.get(0).getChildren().get(0).getName()).isEqualTo("糕点");
        verify(categoryMapper).selectCategoryTree();
    }

    @Test
    @DisplayName("获取分类树 - 空数据")
    void getCategoryTree_Empty() {
        // Given
        when(categoryMapper.selectCategoryTree()).thenReturn(Collections.emptyList());

        // When
        List<CategoryDTO> result = categoryService.getCategoryTree();

        // Then
        assertThat(result).isEmpty();
        verify(categoryMapper).selectCategoryTree();
    }

    @Test
    @DisplayName("根据ID获取分类 - 成功")
    void getCategoryById_Success() {
        // Given
        Long categoryId = 1L;
        ProductCategory category = CategoryTestDataBuilder.defaultCategory();
        when(categoryMapper.selectById(categoryId)).thenReturn(category);

        // When
        CategoryDTO result = categoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(categoryId);
        assertThat(result.getName()).isEqualTo("肇庆特产");
        verify(categoryMapper).selectById(categoryId);
    }

    @Test
    @DisplayName("根据ID获取分类 - 不存在")
    void getCategoryById_NotFound() {
        // Given
        Long categoryId = 999L;
        when(categoryMapper.selectById(categoryId)).thenReturn(null);

        // When
        CategoryDTO result = categoryService.getCategoryById(categoryId);

        // Then
        assertThat(result).isNull();
        verify(categoryMapper).selectById(categoryId);
    }

    @Test
    @DisplayName("根据父分类ID获取子分类 - 成功")
    void getCategoriesByParentId_Success() {
        // Given
        Long parentId = 1L;
        List<ProductCategory> categories = Arrays.asList(
                CategoryTestDataBuilder.categoryWithParent(parentId),
                CategoryTestDataBuilder.childCategory()
        );
        when(categoryMapper.selectByParentId(parentId)).thenReturn(categories);

        // When
        List<CategoryDTO> result = categoryService.getCategoriesByParentId(parentId);

        // Then
        assertThat(result).hasSize(2);
        verify(categoryMapper).selectByParentId(parentId);
    }

    @Test
    @DisplayName("根据父分类ID获取子分类 - 无子分类")
    void getCategoriesByParentId_NoChildren() {
        // Given
        Long parentId = 999L;
        when(categoryMapper.selectByParentId(parentId)).thenReturn(Collections.emptyList());

        // When
        List<CategoryDTO> result = categoryService.getCategoriesByParentId(parentId);

        // Then
        assertThat(result).isEmpty();
        verify(categoryMapper).selectByParentId(parentId);
    }
}
