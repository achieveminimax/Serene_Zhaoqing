package com.zqtravel.scenic.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 景点实体类
 * 对应数据库表：scenic_spots
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scenic_spots")
public class ScenicSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 主图URL
     */
    private String heroImage;

    /**
     * 图片列表（JSON格式）
     */
    private String images;

    /**
     * 空气质量指数
     */
    private Integer aqi;

    /**
     * 温度
     */
    private BigDecimal temperature;

    /**
     * 湿度
     */
    private BigDecimal humidity;

    /**
     * 引用语
     */
    private String quote;

    /**
     * 描述
     */
    private String description;

    /**
     * 次要描述
     */
    private String descriptionSecondary;

    /**
     * 开放时间
     */
    private String openTime;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 距离
     */
    private String distance;

    /**
     * 纬度
     */
    private BigDecimal locationLat;

    /**
     * 经度
     */
    private BigDecimal locationLng;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 收藏次数
     */
    private Integer favoriteCount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    /**
     * 是否删除：0-否，1-是
     */
    @TableLogic
    private Integer isDeleted;
}