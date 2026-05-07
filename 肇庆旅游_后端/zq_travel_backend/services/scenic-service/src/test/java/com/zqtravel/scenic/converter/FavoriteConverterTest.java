package com.zqtravel.scenic.converter;

import com.zqtravel.scenic.dto.response.FavoriteDTO;
import com.zqtravel.scenic.entity.UserFavorite;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FavoriteConverter 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@DisplayName("收藏转换器测试")
class FavoriteConverterTest {

    @Test
    @DisplayName("转换为DTO - 正常实体")
    void toDTO_WithValidEntity_ShouldReturnDTO() {
        // Given
        UserFavorite entity = TestDataFactory.createUserFavorite(1L, "scenic", 1L);

        // When
        FavoriteDTO dto = FavoriteConverter.toDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getUserId()).isEqualTo(1L);
        assertThat(dto.getTargetType()).isEqualTo("scenic");
        assertThat(dto.getTargetId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("转换为DTO - null实体")
    void toDTO_WithNullEntity_ShouldReturnNull() {
        // When
        FavoriteDTO dto = FavoriteConverter.toDTO(null);

        // Then
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("转换为DTO列表 - 正常列表")
    void toDTOList_WithValidList_ShouldReturnDTOList() {
        // Given
        List<UserFavorite> entities = new ArrayList<>();
        entities.add(TestDataFactory.createUserFavorite(1L, "scenic", 1L));
        entities.add(TestDataFactory.createUserFavorite(1L, "music", 2L));
        entities.add(TestDataFactory.createUserFavorite(1L, "recipe", 3L));

        // When
        List<FavoriteDTO> dtos = FavoriteConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(3);
        assertThat(dtos.get(0).getTargetType()).isEqualTo("scenic");
        assertThat(dtos.get(1).getTargetType()).isEqualTo("music");
        assertThat(dtos.get(2).getTargetType()).isEqualTo("recipe");
    }

    @Test
    @DisplayName("转换为DTO列表 - 包含null元素")
    void toDTOList_WithNullElement_ShouldSkipNull() {
        // Given
        List<UserFavorite> entities = new ArrayList<>();
        entities.add(TestDataFactory.createUserFavorite(1L, "scenic", 1L));
        entities.add(null);
        entities.add(TestDataFactory.createUserFavorite(1L, "music", 2L));

        // When
        List<FavoriteDTO> dtos = FavoriteConverter.toDTOList(entities);

        // Then
        assertThat(dtos).hasSize(2);
    }

    @Test
    @DisplayName("转换为DTO列表 - 空列表")
    void toDTOList_WithEmptyList_ShouldReturnEmptyList() {
        // When
        List<FavoriteDTO> dtos = FavoriteConverter.toDTOList(Collections.emptyList());

        // Then
        assertThat(dtos).isEmpty();
    }
}
