package com.zqtravel.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 音乐收藏实体类
 * 对应表: music_favorites
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("music_favorites")
public class MusicFavorite {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 音乐ID
     */
    private Long musicId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}