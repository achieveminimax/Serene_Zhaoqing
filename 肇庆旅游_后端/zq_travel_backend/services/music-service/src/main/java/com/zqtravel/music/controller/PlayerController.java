package com.zqtravel.music.controller;

import com.zqtravel.music.dto.PageRequest;
import com.zqtravel.music.dto.PageResult;
import com.zqtravel.music.dto.PlaylistDTO;
import com.zqtravel.music.dto.request.AddTrackRequest;
import com.zqtravel.music.dto.request.PlaylistCreateRequest;
import com.zqtravel.music.dto.request.PlaylistUpdateRequest;
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
 * 播放器控制器
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@RestController
@RequestMapping("/api/v1/player")
@Tag(name = "播放器管理", description = "播放列表和歌词相关接口")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PlayerController {

    @Operation(summary = "获取播放列表", description = "获取用户的播放列表")
    @GetMapping("/playlist")
    public ResponseEntity<PageResult<PlaylistDTO>> getPlaylists(
            @Valid PageRequest pageRequest,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现获取播放列表逻辑
        return ResponseEntity.ok(PageResult.of(1, 20, 0L, List.of()));
    }

    @Operation(summary = "创建播放列表", description = "创建新的播放列表，名校重复校验")
    @PostMapping("/playlist")
    public ResponseEntity<PlaylistDTO> createPlaylist(
            @Valid @RequestBody PlaylistCreateRequest request,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现创建播放列表逻辑
        return ResponseEntity.ok(new PlaylistDTO());
    }

    @Operation(summary = "更新播放列表", description = "更新播放列表的元数据（名称、简介等）")
    @PutMapping("/playlist/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylist(
            @Parameter(description = "播放列表ID", required = true) 
            @PathVariable Long id,
            @Valid @RequestBody PlaylistUpdateRequest request,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现更新播放列表逻辑，校验用户权限
        return ResponseEntity.ok(new PlaylistDTO());
    }

    @Operation(summary = "删除播放列表", description = "删除播放列表，校验用户所属权限")
    @DeleteMapping("/playlist/{id}")
    public ResponseEntity<Void> deletePlaylist(
            @Parameter(description = "播放列表ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现删除播放列表逻辑，校验用户权限
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "添加歌曲到播放列表", description = "向播放列表添加歌曲，自动校验重复歌曲")
    @PostMapping("/playlist/{id}/tracks")
    public ResponseEntity<Void> addTrackToPlaylist(
            @Parameter(description = "播放列表ID", required = true) 
            @PathVariable Long id,
            @Valid @RequestBody AddTrackRequest request,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现添加歌曲逻辑，校验重复歌曲
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "从播放列表移除歌曲", description = "从指定播放列表中删除单曲")
    @DeleteMapping("/playlist/{id}/tracks/{trackId}")
    public ResponseEntity<Void> removeTrackFromPlaylist(
            @Parameter(description = "播放列表ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "歌曲ID", required = true) 
            @PathVariable Long trackId,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现移除歌曲逻辑，校验用户权限
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "获取歌词", description = "获取音乐的歌词内容")
    @GetMapping("/lyrics/{musicId}")
    public ResponseEntity<String> getLyrics(
            @Parameter(description = "音乐ID", required = true) 
            @PathVariable Long musicId) {
        // TODO: 实现获取歌词逻辑
        return ResponseEntity.ok("");
    }

    @Operation(summary = "获取播放列表详情", description = "获取播放列表的完整信息，包括歌曲详情")
    @GetMapping("/playlist/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistDetail(
            @Parameter(description = "播放列表ID", required = true) 
            @PathVariable Long id,
            @Parameter(description = "用户ID", required = true) 
            @RequestHeader("X-User-Id") Long userId) {
        // TODO: 实现获取播放列表详情逻辑
        return ResponseEntity.ok(new PlaylistDTO());
    }
}