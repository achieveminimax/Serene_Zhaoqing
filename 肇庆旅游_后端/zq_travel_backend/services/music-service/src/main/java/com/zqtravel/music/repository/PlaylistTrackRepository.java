package com.zqtravel.music.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.music.entity.PlaylistTrack;
import org.apache.ibatis.annotations.Mapper;

/**
 * 播放列表音乐关联数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface PlaylistTrackRepository extends BaseMapper<PlaylistTrack> {
}