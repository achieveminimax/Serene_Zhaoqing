package com.zqtravel.music.service;

import com.zqtravel.music.dto.MusicDTO;
import com.zqtravel.music.dto.PageResult;
import com.zqtravel.music.dto.request.MusicQueryRequest;

import java.util.List;

/**
 * 音乐服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface MusicService {

    /**
     * 分页查询音乐列表
     * 
     * @param request 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MusicDTO> getMusics(MusicQueryRequest request, Integer page, Integer size);

    /**
     * 根据ID获取音乐详情
     * 
     * @param id 音乐ID
     * @param userId 用户ID（用于判断是否收藏）
     * @return 音乐信息
     */
    MusicDTO getMusicById(Long id, Long userId);

    /**
     * 获取舒缓音乐列表
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MusicDTO> getRelaxMusics(Integer page, Integer size);

    /**
     * 获取自然音效列表
     * 
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortOrder 排序方向
     * @return 分页结果
     */
    PageResult<MusicDTO> getNatureSounds(Integer page, Integer size, String sortBy, String sortOrder);

    /**
     * 获取冥想音乐列表
     * 
     * @param categoryId 分类ID（可选）
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MusicDTO> getMeditationMusics(Long categoryId, Integer page, Integer size);

    /**
     * 获取推荐音乐列表
     * 
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MusicDTO> getRecommendMusics(Long userId, Integer page, Integer size);

    /**
     * 记录播放次数
     * 
     * @param musicId 音乐ID
     * @param userId 用户ID
     */
    void recordPlayCount(Long musicId, Long userId);

    /**
     * 获取歌词
     * 
     * @param musicId 音乐ID
     * @return 歌词内容
     */
    String getLyrics(Long musicId);
}