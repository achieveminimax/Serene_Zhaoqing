package com.zqtravel.music.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 音乐实体类
 * 对应表: musics
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("musics")
public class Music {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 音乐名称
     */
    private String name;

    /**
     * 艺术家
     */
    private String artist;

    /**
     * 表情符号
     */
    private String emoji;

    /**
     * 标签
     */
    private String tag;

    /**
     * 时长(秒)
     */
    private Integer duration;

    /**
     * 封面图URL
     */
    private String imageUrl;

    /**
     * 音频文件URL
     */
    private String audioUrl;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 歌词
     */
    private String lyrics;

    /**
     * 播放次数
     */
    private Integer playCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 状态: 0-下架, 1-上架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 是否删除: 0-否, 1-是
     */
    @TableField("is_deleted")
    private Integer deleted;
}