package com.zqtravel.scenic.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 景点响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "景点响应DTO")
public class ScenicSpotDTO {

    @Schema(description = "景点ID", example = "1")
    private Long id;

    @Schema(description = "景点名称", example = "七星岩")
    private String name;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", example = "自然风光")
    private String categoryName;

    @Schema(description = "主图URL", example = "https://example.com/images/qixingyan.jpg")
    private String heroImage;

    @Schema(description = "图片列表")
    private List<String> images;

    @Schema(description = "空气质量指数", example = "45")
    private Integer aqi;

    @Schema(description = "温度", example = "25.5")
    private BigDecimal temperature;

    @Schema(description = "湿度", example = "65.2")
    private BigDecimal humidity;

    @Schema(description = "引用语", example = "桂林山水甲天下，肇庆七星岩更奇")
    private String quote;

    @Schema(description = "描述", example = "七星岩是肇庆著名的自然景观，由七座石灰岩山峰组成...")
    private String description;

    @Schema(description = "次要描述", example = "景区内有多处溶洞和地下河，景色秀丽...")
    private String descriptionSecondary;

    @Schema(description = "开放时间", example = "08:00-18:00")
    private String openTime;

    @Schema(description = "难度", example = "中等")
    private String difficulty;

    @Schema(description = "距离", example = "距离市区5公里")
    private String distance;

    @Schema(description = "纬度", example = "23.0516")
    private BigDecimal locationLat;

    @Schema(description = "经度", example = "112.4587")
    private BigDecimal locationLng;

    @Schema(description = "地址", example = "肇庆市端州区七星岩景区")
    private String address;

    @Schema(description = "状态：0-下架，1-上架", example = "1")
    private Integer status;

    @Schema(description = "浏览次数", example = "1250")
    private Integer viewCount;

    @Schema(description = "收藏次数", example = "320")
    private Integer favoriteCount;

    @Schema(description = "是否已收藏（需要用户登录）", example = "true")
    private Boolean isFavorite = false;

    @Schema(description = "距离（公里，仅附近景点查询时返回）", example = "2.5")
    private Double distanceKm;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;
}