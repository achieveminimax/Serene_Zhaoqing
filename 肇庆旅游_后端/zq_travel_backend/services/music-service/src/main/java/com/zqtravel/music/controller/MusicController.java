package com.zqtravel.music.controller;

import com.zqtravel.music.dto.MusicCategoryDTO;
import com.zqtravel.music.dto.MusicDTO;
import com.zqtravel.music.dto.PageRequest;
import com.zqtravel.music.dto.PageResult;
import com.zqtravel.music.dto.request.MusicQueryRequest;
import com.zqtravel.music.service.MusicCategoryService;
import com.zqtravel.music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 音乐控制器
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@RestController
@RequestMapping("/api/v1/music")
@Tag(name = "音乐管理", description = "音乐相关接口")
@Slf4j
@RequiredArgsConstructor
@Validated
public class MusicController {

    private final MusicCategoryService musicCategoryService;
    private final MusicService musicService;

    @Operation(summary = "获取音乐分类", description = "分页获取全部可用音乐分类")
    @GetMapping("/categories")
    public ResponseEntity<PageResult<MusicCategoryDTO>> getCategories(@Valid PageRequest pageRequest) {
        PageResult<MusicCategoryDTO> result = musicCategoryService.getCategories(
            pageRequest.getPage(), pageRequest.getSize());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "获取所有音乐分类", description = "获取所有音乐分类（不分页）")
    @GetMapping("/categories/all")
    public ResponseEntity<List<MusicCategoryDTO>> getAllCategories() {
        List<MusicCategoryDTO> categories = musicCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "舒缓音乐列表", description = "获取舒缓音乐列表，支持分页")
    @GetMapping("/relax")
    public ResponseEntity<PageResult<MusicDTO>> getRelaxMusics(@Valid PageRequest pageRequest) {
        PageResult<MusicDTO> result = musicService.getRelaxMusics(
            pageRequest.getPage(), pageRequest.getSize());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "自然音效列表", description = "获取自然音效列表，支持排序和分页")
    @GetMapping("/nature-sounds")
    public ResponseEntity<PageResult<MusicDTO>> getNatureSounds(
            @Valid PageRequest pageRequest,
            @Parameter(description = "排序字段: play_count, favorite_count, created_at") 
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "排序方向: asc, desc") 
            @RequestParam(required = false) String sortOrder) {
        PageResult<MusicDTO> result = musicService.getNatureSounds(
            pageRequest.getPage(), pageRequest.getSize(), sortBy, sortOrder);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "冥想音乐列表", description = "获取冥想音乐列表，可按分类筛选")
    @GetMapping("/meditation")
    public ResponseEntity<PageResult<MusicDTO>> getMeditationMusics(
            @Valid PageRequest pageRequest,
            @Parameter(description = "分类ID") 
            @RequestParam(required = false) Long categoryId) {
        PageResult<MusicDTO> result = musicService.getMeditationMusics(
            categoryId, pageRequest.getPage(), pageRequest.getSize());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "音乐推荐列表", description = "获取个性化音乐推荐列表")
    @GetMapping("/recommends")
    public ResponseEntity<PageResult<MusicDTO>> getRecommendMusics(
            @Valid PageRequest pageRequest,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        PageResult<MusicDTO> result = musicService.getRecommendMusics(
            userId, pageRequest.getPage(), pageRequest.getSize());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "记录播放次数", description = "记录音乐播放次数，自带并发控制机制")
    @PostMapping("/{id}/play")
    public ResponseEntity<Void> recordPlayCount(
            @Parameter(description = "音乐ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        musicService.recordPlayCount(id, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "收藏/取消收藏", description = "切换音乐的收藏状态")
    @PostMapping("/{id}/favorite")
    public ResponseEntity<Void> toggleFavorite(
            @Parameter(description = "音乐ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现收藏/取消收藏逻辑
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "获取收藏音乐", description = "获取指定用户的收藏音乐列表")
    @GetMapping("/favorites")
    public ResponseEntity<PageResult<MusicDTO>> getFavoriteMusics(
            @Valid PageRequest pageRequest,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现获取收藏音乐逻辑
        return ResponseEntity.ok(PageResult.of(1, 20, 0L, List.of()));
    }

    @Operation(summary = "查询音乐列表", description = "根据条件查询音乐列表")
    @GetMapping("/search")
    public ResponseEntity<PageResult<MusicDTO>> searchMusics(
            @Valid MusicQueryRequest request,
            @Valid PageRequest pageRequest) {
        PageResult<MusicDTO> result = musicService.getMusics(
            request, pageRequest.getPage(), pageRequest.getSize());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "获取音乐详情", description = "根据ID获取音乐详情")
    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusicById(
            @Parameter(description = "音乐ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户ID") 
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        MusicDTO music = musicService.getMusicById(id, userId);
        if (music == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(music);
    }
}