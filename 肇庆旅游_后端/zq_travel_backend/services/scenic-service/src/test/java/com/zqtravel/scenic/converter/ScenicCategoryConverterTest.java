package com.zqtravel.scenic.converter;

import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;
import com.zqtravel.scenic.entity.ScenicCategory;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ScenicCategoryConverter 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@DisplayName("景点分类转换器测试")
class ScenicCategoryConverterTest {

    @Test
    @DisplayName("转换为DTO - 正常实体")
    void toDTO_WithValidEntity_ShouldReturnDTO() {
        // Given
        ScenicCategory entity = TestDataFactory.createScenicCategory(1L, "自然风光");

        // When
        ScenicCategoryDTO dto = ScenicCategoryConverter.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("自然风光");
        assertThat(dto.getIcon()).isNotNull();
        assertThat(dto.getSortOrder()).isEqualTo(1);
    }

    @Test
    @DisplayName("转换为DTO - null实体")
    void toDTO_WithNullEntity_ShouldReturnNull() {
        // When
        ScenicCategoryDTO dto = ScenicCategoryConverter.toDTO(null);

        // Then
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("转换为DTO - 空名称")
    void toDTO_WithEmptyName_ShouldReturnDTO() {
        // Given
        ScenicCategory entity = TestDataFactory.createScenicCategory(1L, "");

        // When
        ScenicCategoryDTO dto = ScenicCategoryConverter.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEmpty();
    }

    @Test
    @DisplayName("转换为DTO列表 - 正常列表")
    void toDTOList_WithValidList_ShouldReturnDTOList() {
        // Given
        List<ScenicCategory> entities = TestDataFactory.createScenicCategoryList(3);

        // When
        List<ScenicCategoryDTO> dtos = ScenicCategoryConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(3);
        assertThat(dtos.get(0).getName()).isEqualTo("分类1");
        assertThat(dtos.get(1).getName()).isEqualTo("分类2");
        assertThat(dtos.get(2).getName()).isEqualTo("分类3");
    }

    @Test
    @DisplayName("转换为DTO列表 - 包含null元素")
    void toDTOList_WithNullElement_ShouldSkipNull() {
        // Given
        List<ScenicCategory> entities = new ArrayList<>();
        entities.add(TestDataFactory.createScenicCategory(1L, "分类1"));
        entities.add(null);
        entities.add(TestDataFactory.createScenicCategory(2L, "分类2"));

        // When
        List<ScenicCategoryDTO> dtos = ScenicCategoryConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(2);
    }

    @Test
    @DisplayName("转换为DTO列表 - 空列表")
    void toDTOList_WithEmptyList_ShouldReturnEmptyList() {
        // When
        List<ScenicCategoryDTO> dtos = ScenicCategoryConverter.toDTOList(Collections.emptyList());

        // Then
        assertThat(dtos).isEmpty();
    }

    @Test
    @DisplayName("转换为DTO列表 - 所有null元素")
    void toDTOList_WithAllNullElements_ShouldReturnEmptyList() {
        // Given
        List<ScenicCategory> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);

        // When
        List<ScenicCategoryDTO> dtos = ScenicCategoryConverter.toDTOList(entities);

        // Then
        assertThat(dtos).isEmpty();
    }
}
