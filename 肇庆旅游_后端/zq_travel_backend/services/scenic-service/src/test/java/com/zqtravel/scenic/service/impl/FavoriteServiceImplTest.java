package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.dto.response.FavoriteDTO;
import com.zqtravel.scenic.entity.UserFavorite;
import com.zqtravel.scenic.exception.FavoriteException;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.repository.UserFavoriteRepository;
import com.zqtravel.scenic.util.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * FavoriteServiceImpl 单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("收藏服务测试")
class FavoriteServiceImplTest {

    @Mock
    private UserFavoriteRepository userFavoriteRepository;

    @Mock
    private ScenicSpotRepository scenicSpotRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    @Test
    @DisplayName("添加收藏 - 正常添加")
    void addFavorite_WithValidParams_ShouldAddFavorite() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);
        given(scenicSpotRepository.existsById(targetId)).willReturn(true);
        given(userFavoriteRepository.insert(any(UserFavorite.class))).willAnswer(invocation -> {
            UserFavorite favorite = invocation.getArgument(0);
            favorite.setId(1L);
            return 1;
        });
        given(scenicSpotRepository.incrementFavoriteCount(targetId)).willReturn(1);

        // When
        Long result = favoriteService.addFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(1L);
        verify(userFavoriteRepository).insert(any(UserFavorite.class));
        verify(scenicSpotRepository).incrementFavoriteCount(targetId);
    }

    @Test
    @DisplayName("添加收藏 - 已收藏")
    void addFavorite_WithAlreadyFavorited_ShouldThrowException() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;
        UserFavorite existingFavorite = TestDataFactory.createUserFavorite(userId, targetType, targetId);

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(existingFavorite);

        // When & Then
        assertThatThrownBy(() -> favoriteService.addFavorite(userId, targetType, targetId))
                .isInstanceOf(FavoriteException.class)
                .hasMessageContaining("已收藏");
    }

    @Test
    @DisplayName("添加收藏 - 景点不存在")
    void addFavorite_WithNonExistingSpot_ShouldThrowException() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 999L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);
        given(scenicSpotRepository.existsById(targetId)).willReturn(false);

        // When & Then
        assertThatThrownBy(() -> favoriteService.addFavorite(userId, targetType, targetId))
                .isInstanceOf(FavoriteException.class)
                .hasMessageContaining("不存在");
    }

    @Test
    @DisplayName("添加收藏 - 插入失败")
    void addFavorite_WithInsertFailure_ShouldThrowException() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);
        given(scenicSpotRepository.existsById(targetId)).willReturn(true);
        given(userFavoriteRepository.insert(any(UserFavorite.class))).willReturn(0);

        // When & Then
        assertThatThrownBy(() -> favoriteService.addFavorite(userId, targetType, targetId))
                .isInstanceOf(FavoriteException.class)
                .hasMessageContaining("添加收藏失败");
    }

    @Test
    @DisplayName("取消收藏 - 正常取消")
    void removeFavorite_WithExistingFavorite_ShouldRemove() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;
        UserFavorite existingFavorite = TestDataFactory.createUserFavorite(userId, targetType, targetId);

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(existingFavorite);
        given(userFavoriteRepository.delete(any(QueryWrapper.class))).willReturn(1);
        given(scenicSpotRepository.decrementFavoriteCount(targetId)).willReturn(1);

        // When
        boolean result = favoriteService.removeFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isTrue();
        verify(userFavoriteRepository).delete(any(QueryWrapper.class));
        verify(scenicSpotRepository).decrementFavoriteCount(targetId);
    }

    @Test
    @DisplayName("取消收藏 - 未收藏")
    void removeFavorite_WithNotFavorited_ShouldThrowException() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);

        // When & Then
        assertThatThrownBy(() -> favoriteService.removeFavorite(userId, targetType, targetId))
                .isInstanceOf(FavoriteException.class)
                .hasMessageContaining("未收藏");
    }

    @Test
    @DisplayName("取消收藏 - 删除失败")
    void removeFavorite_WithDeleteFailure_ShouldThrowException() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;
        UserFavorite existingFavorite = TestDataFactory.createUserFavorite(userId, targetType, targetId);

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(existingFavorite);
        given(userFavoriteRepository.delete(any(QueryWrapper.class))).willReturn(0);

        // When & Then
        assertThatThrownBy(() -> favoriteService.removeFavorite(userId, targetType, targetId))
                .isInstanceOf(FavoriteException.class)
                .hasMessageContaining("取消收藏失败");
    }

    @Test
    @DisplayName("获取用户收藏列表 - 按类型查询")
    void getUserFavorites_WithTargetType_ShouldReturnFavorites() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        List<UserFavorite> favorites = Collections.singletonList(
                TestDataFactory.createUserFavorite(userId, targetType, 1L)
        );

        given(userFavoriteRepository.selectUserFavorites(userId, targetType))
                .willReturn(favorites);

        // When
        List<FavoriteDTO> result = favoriteService.getUserFavorites(userId, targetType);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTargetType()).isEqualTo(targetType);
    }

    @Test
    @DisplayName("获取用户收藏列表 - 查询所有类型")
    void getUserFavorites_WithNullTargetType_ShouldReturnAllFavorites() {
        // Given
        Long userId = 1L;
        List<UserFavorite> favorites = new ArrayList<>();
        favorites.add(TestDataFactory.createUserFavorite(userId, "scenic", 1L));
        favorites.add(TestDataFactory.createUserFavorite(userId, "music", 2L));

        given(userFavoriteRepository.selectList(any(QueryWrapper.class)))
                .willReturn(favorites);

        // When
        List<FavoriteDTO> result = favoriteService.getUserFavorites(userId, null);

        // Then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("获取用户收藏列表 - 空结果")
    void getUserFavorites_WithNoFavorites_ShouldReturnEmptyList() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";

        given(userFavoriteRepository.selectUserFavorites(userId, targetType))
                .willReturn(new ArrayList<>());

        // When
        List<FavoriteDTO> result = favoriteService.getUserFavorites(userId, targetType);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("检查是否已收藏 - 已收藏")
    void isFavorite_WithExistingFavorite_ShouldReturnTrue() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;
        UserFavorite favorite = TestDataFactory.createUserFavorite(userId, targetType, targetId);

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(favorite);

        // When
        boolean result = favoriteService.isFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("检查是否已收藏 - 未收藏")
    void isFavorite_WithNoFavorite_ShouldReturnFalse() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);

        // When
        boolean result = favoriteService.isFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("获取用户收藏数量 - 按类型统计")
    void getUserFavoriteCount_WithTargetType_ShouldReturnCount() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";

        given(userFavoriteRepository.countUserFavorites(userId, targetType)).willReturn(5);

        // When
        Integer result = favoriteService.getUserFavoriteCount(userId, targetType);

        // Then
        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("获取用户收藏数量 - 统计所有类型")
    void getUserFavoriteCount_WithNullTargetType_ShouldReturnTotalCount() {
        // Given
        Long userId = 1L;

        given(userFavoriteRepository.selectCount(any(QueryWrapper.class))).willReturn(10L);

        // When
        Integer result = favoriteService.getUserFavoriteCount(userId, null);

        // Then
        assertThat(result).isEqualTo(10);
    }

    @Test
    @DisplayName("切换收藏状态 - 从未收藏到收藏")
    void toggleFavorite_FromNotFavoritedToFavorited_ShouldAddFavorite() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(null);
        given(scenicSpotRepository.existsById(targetId)).willReturn(true);
        given(userFavoriteRepository.insert(any(UserFavorite.class))).willReturn(1);
        given(scenicSpotRepository.incrementFavoriteCount(targetId)).willReturn(1);

        // When
        boolean result = favoriteService.toggleFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isTrue();
        verify(userFavoriteRepository).insert(any(UserFavorite.class));
    }

    @Test
    @DisplayName("切换收藏状态 - 从收藏到取消收藏")
    void toggleFavorite_FromFavoritedToNotFavorited_ShouldRemoveFavorite() {
        // Given
        Long userId = 1L;
        String targetType = "scenic";
        Long targetId = 1L;
        UserFavorite existingFavorite = TestDataFactory.createUserFavorite(userId, targetType, targetId);

        given(userFavoriteRepository.selectUserFavorite(userId, targetType, targetId))
                .willReturn(existingFavorite);
        given(userFavoriteRepository.delete(any(QueryWrapper.class))).willReturn(1);
        given(scenicSpotRepository.decrementFavoriteCount(targetId)).willReturn(1);

        // When
        boolean result = favoriteService.toggleFavorite(userId, targetType, targetId);

        // Then
        assertThat(result).isFalse();
        verify(userFavoriteRepository).delete(any(QueryWrapper.class));
    }
}