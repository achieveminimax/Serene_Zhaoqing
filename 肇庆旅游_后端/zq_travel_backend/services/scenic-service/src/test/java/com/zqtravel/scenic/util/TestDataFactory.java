package com.zqtravel.scenic.util;

import com.zqtravel.scenic.dto.request.FavoriteRequest;
import com.zqtravel.scenic.dto.request.NearbyScenicRequest;
import com.zqtravel.scenic.dto.request.ScenicQueryRequest;
import com.zqtravel.scenic.dto.response.*;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.entity.ScenicCategory;
import com.zqtravel.scenic.entity.ScenicSpot;
import com.zqtravel.scenic.entity.UserFavorite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试数据工厂
 * 用于创建测试中使用的各种数据对象
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public class TestDataFactory {

    // 默认常量
    public static final Long DEFAULT_USER_ID = 1L;
    public static final Long DEFAULT_SPOT_ID = 1L;
    public static final Long DEFAULT_CATEGORY_ID = 1L;
    public static final String DEFAULT_SPOT_NAME = "七星岩";
    public static final String DEFAULT_CATEGORY_NAME = "自然风光";
    public static final BigDecimal DEFAULT_LAT = new BigDecimal("23.0516");
    public static final BigDecimal DEFAULT_LNG = new BigDecimal("112.4587");
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_SIZE = 20;

    /**
     * 创建默认景点实体
     */
    public static ScenicSpot createScenicSpot() {
        return createScenicSpot(DEFAULT_SPOT_ID, DEFAULT_SPOT_NAME);
    }

    /**
     * 创建指定ID和名称的景点实体
     */
    public static ScenicSpot createScenicSpot(Long id, String name) {
        ScenicSpot spot = new ScenicSpot();
        spot.setId(id);
        spot.setName(name);
        spot.setCategoryId(DEFAULT_CATEGORY_ID);
        spot.setHeroImage("https://example.com/images/spot" + id + ".jpg");
        spot.setImages("[\"https://example.com/images/spot" + id + "_1.jpg\"]");
        spot.setAqi(45);
        spot.setTemperature(new BigDecimal("25.5"));
        spot.setHumidity(new BigDecimal("65.2"));
        spot.setQuote("桂林山水甲天下，肇庆七星岩更奇");
        spot.setDescription("七星岩是肇庆著名的自然景观");
        spot.setDescriptionSecondary("景区内有多处溶洞和地下河");
        spot.setOpenTime("08:00-18:00");
        spot.setDifficulty("中等");
        spot.setDistance("距离市区5公里");
        spot.setLocationLat(DEFAULT_LAT);
        spot.setLocationLng(DEFAULT_LNG);
        spot.setAddress("肇庆市端州区七星岩景区");
        spot.setStatus(1);
        spot.setViewCount(1000);
        spot.setFavoriteCount(200);
        spot.setCreatedAt(new Date());
        spot.setUpdatedAt(new Date());
        spot.setIsDeleted(0);
        return spot;
    }

    /**
     * 创建景点实体列表
     */
    public static List<ScenicSpot> createScenicSpotList(int count) {
        List<ScenicSpot> spots = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            spots.add(createScenicSpot((long) i, "景点" + i));
        }
        return spots;
    }

    /**
     * 创建默认景点DTO
     */
    public static ScenicSpotDTO createScenicSpotDTO() {
        return createScenicSpotDTO(DEFAULT_SPOT_ID, DEFAULT_SPOT_NAME);
    }

    /**
     * 创建指定ID和名称的景点DTO
     */
    public static ScenicSpotDTO createScenicSpotDTO(Long id, String name) {
        ScenicSpotDTO dto = new ScenicSpotDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setCategoryId(DEFAULT_CATEGORY_ID);
        dto.setCategoryName(DEFAULT_CATEGORY_NAME);
        dto.setHeroImage("https://example.com/images/spot" + id + ".jpg");
        dto.setAqi(45);
        dto.setTemperature(new BigDecimal("25.5"));
        dto.setHumidity(new BigDecimal("65.2"));
        dto.setQuote("桂林山水甲天下，肇庆七星岩更奇");
        dto.setDescription("七星岩是肇庆著名的自然景观");
        dto.setDescriptionSecondary("景区内有多处溶洞和地下河");
        dto.setOpenTime("08:00-18:00");
        dto.setDifficulty("中等");
        dto.setDistance("距离市区5公里");
        dto.setLocationLat(DEFAULT_LAT);
        dto.setLocationLng(DEFAULT_LNG);
        dto.setAddress("肇庆市端州区七星岩景区");
        dto.setStatus(1);
        dto.setViewCount(1000);
        dto.setFavoriteCount(200);
        dto.setIsFavorite(false);
        dto.setCreatedAt(new Date());
        dto.setUpdatedAt(new Date());
        return dto;
    }

    /**
     * 创建默认景点详情VO
     */
    public static ScenicDetailVO createScenicDetailVO() {
        ScenicDetailVO vo = new ScenicDetailVO();
        vo.setId(DEFAULT_SPOT_ID);
        vo.setName(DEFAULT_SPOT_NAME);
        vo.setCategoryId(DEFAULT_CATEGORY_ID);
        vo.setCategoryName(DEFAULT_CATEGORY_NAME);
        vo.setHeroImage("https://example.com/images/spot1.jpg");
        vo.setAqi(45);
        vo.setAqiLevel("优");
        vo.setTemperature(new BigDecimal("25.5"));
        vo.setHumidity(new BigDecimal("65.2"));
        vo.setQuote("桂林山水甲天下，肇庆七星岩更奇");
        vo.setDescription("七星岩是肇庆著名的自然景观");
        vo.setDescriptionSecondary("景区内有多处溶洞和地下河");
        vo.setOpenTime("08:00-18:00");
        vo.setDifficulty("中等");
        vo.setDistance("距离市区5公里");
        vo.setLocationLat(DEFAULT_LAT);
        vo.setLocationLng(DEFAULT_LNG);
        vo.setAddress("肇庆市端州区七星岩景区");
        vo.setStatus(1);
        vo.setViewCount(1000);
        vo.setFavoriteCount(200);
        vo.setIsFavorite(false);
        vo.setCreatedAt(new Date());
        vo.setUpdatedAt(new Date());
        return vo;
    }

    /**
     * 创建默认分类实体
     */
    public static ScenicCategory createScenicCategory() {
        return createScenicCategory(DEFAULT_CATEGORY_ID, DEFAULT_CATEGORY_NAME);
    }

    /**
     * 创建指定ID和名称的分类实体
     */
    public static ScenicCategory createScenicCategory(Long id, String name) {
        ScenicCategory category = new ScenicCategory();
        category.setId(id);
        category.setName(name);
        category.setIcon("https://example.com/icons/category" + id + ".png");
        category.setSortOrder(id.intValue());
        category.setCreatedAt(new Date());
        return category;
    }

    /**
     * 创建分类实体列表
     */
    public static List<ScenicCategory> createScenicCategoryList(int count) {
        List<ScenicCategory> categories = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            categories.add(createScenicCategory((long) i, "分类" + i));
        }
        return categories;
    }

    /**
     * 创建默认分类DTO
     */
    public static ScenicCategoryDTO createScenicCategoryDTO() {
        ScenicCategoryDTO dto = new ScenicCategoryDTO();
        dto.setId(DEFAULT_CATEGORY_ID);
        dto.setName(DEFAULT_CATEGORY_NAME);
        dto.setIcon("https://example.com/icons/category1.png");
        dto.setSortOrder(1);
        dto.setSpotCount(10);
        dto.setCreatedAt(new Date());
        return dto;
    }

    /**
     * 创建默认收藏实体
     */
    public static UserFavorite createUserFavorite() {
        return createUserFavorite(DEFAULT_USER_ID, "scenic", DEFAULT_SPOT_ID);
    }

    /**
     * 创建指定参数的收藏实体
     */
    public static UserFavorite createUserFavorite(Long userId, String targetType, Long targetId) {
        UserFavorite favorite = new UserFavorite();
        favorite.setId(1L);
        favorite.setUserId(userId);
        favorite.setTargetType(targetType);
        favorite.setTargetId(targetId);
        favorite.setCreatedAt(new Date());
        return favorite;
    }

    /**
     * 创建默认收藏DTO
     */
    public static FavoriteDTO createFavoriteDTO() {
        FavoriteDTO dto = new FavoriteDTO();
        dto.setId(1L);
        dto.setUserId(DEFAULT_USER_ID);
        dto.setTargetType("scenic");
        dto.setTargetId(DEFAULT_SPOT_ID);
        dto.setTargetName(DEFAULT_SPOT_NAME);
        dto.setTargetImage("https://example.com/images/spot1.jpg");
        dto.setTargetDescription("七星岩是肇庆著名的自然景观");
        dto.setCreatedAt(new Date());
        return dto;
    }

    /**
     * 创建默认查询请求
     */
    public static ScenicQueryRequest createScenicQueryRequest() {
        ScenicQueryRequest request = new ScenicQueryRequest();
        request.setPage(DEFAULT_PAGE);
        request.setSize(DEFAULT_SIZE);
        request.setCategoryId(null);
        request.setStatus(1);
        request.setKeyword(null);
        request.setSortBy("created_at");
        request.setSortDirection("desc");
        return request;
    }

    /**
     * 创建默认附近景点请求
     */
    public static NearbyScenicRequest createNearbyScenicRequest() {
        NearbyScenicRequest request = new NearbyScenicRequest();
        request.setLat(DEFAULT_LAT);
        request.setLng(DEFAULT_LNG);
        request.setRadius(10.0);
        request.setLimit(20);
        return request;
    }

    /**
     * 创建默认收藏请求
     */
    public static FavoriteRequest createFavoriteRequest() {
        FavoriteRequest request = new FavoriteRequest();
        request.setUserId(DEFAULT_USER_ID);
        request.setAction("add");
        return request;
    }

    /**
     * 创建默认轮播图DTO
     */
    public static BannerDTO createBannerDTO() {
        BannerDTO dto = new BannerDTO();
        dto.setId(1L);
        dto.setTitle("七星岩美景");
        dto.setImageUrl("https://example.com/images/banner1.jpg");
        dto.setLinkUrl("/pages/scenic-detail/scenic-detail?id=1");
        dto.setSortOrder(1);
        dto.setEnabled(true);
        return dto;
    }

    /**
     * 创建默认推荐DTO
     */
    public static RecommendDTO createRecommendDTO() {
        RecommendDTO dto = new RecommendDTO();
        dto.setId(1L);
        dto.setType("scenic");
        dto.setTitle("热门景点推荐");
        dto.setSubtitle("探索肇庆最美风景");
        dto.setImageUrl("https://example.com/images/recommend1.jpg");
        dto.setLinkUrl("/pages/scenic-list/scenic-list?category=hot");
        dto.setSortOrder(1);
        dto.setEnabled(true);
        return dto;
    }

    /**
     * 创建默认快捷操作DTO
     */
    public static QuickActionDTO createQuickActionDTO() {
        QuickActionDTO dto = new QuickActionDTO();
        dto.setId(1L);
        dto.setName("景点搜索");
        dto.setIconUrl("https://example.com/icons/search.png");
        dto.setLinkUrl("/pages/search/search");
        dto.setBackgroundColor("#4CAF50");
        dto.setSortOrder(1);
        dto.setEnabled(true);
        return dto;
    }
}