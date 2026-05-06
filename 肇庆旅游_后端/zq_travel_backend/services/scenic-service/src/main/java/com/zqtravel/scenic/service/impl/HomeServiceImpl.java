package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.dto.response.BannerDTO;
import com.zqtravel.scenic.dto.response.QuickActionDTO;
import com.zqtravel.scenic.dto.response.RecommendDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.entity.ScenicSpot;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.service.HomeService;
import com.zqtravel.scenic.service.ScenicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页服务实现类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final ScenicService scenicService;
    private final ScenicSpotRepository scenicSpotRepository;

    @Value("${home.banner.enabled:true}")
    private boolean bannerEnabled;

    @Value("${home.recommend.enabled:true}")
    private boolean recommendEnabled;

    @Value("${home.quick-action.enabled:true}")
    private boolean quickActionEnabled;

    @Override
    public List<BannerDTO> getBanners() {
        log.debug("获取轮播图列表");

        if (!bannerEnabled) {
            return new ArrayList<>();
        }

        // 这里使用静态数据，后期可以改为从数据库或配置中心获取
        List<BannerDTO> banners = new ArrayList<>();

        BannerDTO banner1 = new BannerDTO();
        banner1.setId(1L);
        banner1.setTitle("七星岩美景");
        banner1.setImageUrl("https://example.com/images/banner1.jpg");
        banner1.setLinkUrl("/pages/scenic-detail/scenic-detail?id=1");
        banner1.setSortOrder(1);
        banner1.setEnabled(true);
        banners.add(banner1);

        BannerDTO banner2 = new BannerDTO();
        banner2.setId(2L);
        banner2.setTitle("鼎湖山风光");
        banner2.setImageUrl("https://example.com/images/banner2.jpg");
        banner2.setLinkUrl("/pages/scenic-detail/scenic-detail?id=2");
        banner2.setSortOrder(2);
        banner2.setEnabled(true);
        banners.add(banner2);

        BannerDTO banner3 = new BannerDTO();
        banner3.setId(3L);
        banner3.setTitle("星湖湿地公园");
        banner3.setImageUrl("https://example.com/images/banner3.jpg");
        banner3.setLinkUrl("/pages/scenic-detail/scenic-detail?id=3");
        banner3.setSortOrder(3);
        banner3.setEnabled(true);
        banners.add(banner3);

        return banners;
    }

    @Override
    public List<RecommendDTO> getHomeRecommends() {
        log.debug("获取首页推荐内容");

        if (!recommendEnabled) {
            return new ArrayList<>();
        }

        // 这里使用静态数据，后期可以改为从数据库或配置中心获取
        List<RecommendDTO> recommends = new ArrayList<>();

        RecommendDTO recommend1 = new RecommendDTO();
        recommend1.setId(1L);
        recommend1.setType("scenic");
        recommend1.setTitle("热门景点推荐");
        recommend1.setSubtitle("探索肇庆最美风景");
        recommend1.setImageUrl("https://example.com/images/recommend1.jpg");
        recommend1.setLinkUrl("/pages/scenic-list/scenic-list?category=hot");
        recommend1.setSortOrder(1);
        recommend1.setEnabled(true);
        recommends.add(recommend1);

        RecommendDTO recommend2 = new RecommendDTO();
        recommend2.setId(2L);
        recommend2.setType("music");
        recommend2.setTitle("疗愈音乐");
        recommend2.setSubtitle("放松心情的自然音乐");
        recommend2.setImageUrl("https://example.com/images/recommend2.jpg");
        recommend2.setLinkUrl("/pages/music-list/music-list");
        recommend2.setSortOrder(2);
        recommend2.setEnabled(true);
        recommends.add(recommend2);

        RecommendDTO recommend3 = new RecommendDTO();
        recommend3.setId(3L);
        recommend3.setType("recipe");
        recommend3.setTitle("特色美食");
        recommend3.setSubtitle("品尝肇庆地道美食");
        recommend3.setImageUrl("https://example.com/images/recommend3.jpg");
        recommend3.setLinkUrl("/pages/recipe-list/recipe-list");
        recommend3.setSortOrder(3);
        recommend3.setEnabled(true);
        recommends.add(recommend3);

        return recommends;
    }

    @Override
    public List<ScenicSpotDTO> getScenicListForHome(Integer limit) {
        log.debug("获取首页景点推荐，限制数量: {}", limit);

        int defaultLimit = limit != null ? limit : 6;
        
        // 获取热门景点作为首页推荐
        return scenicService.getHotSpots(defaultLimit);
    }

    @Override
    public List<QuickActionDTO> getQuickActions() {
        log.debug("获取快捷操作入口");

        if (!quickActionEnabled) {
            return new ArrayList<>();
        }

        // 这里使用静态数据，后期可以改为从数据库或配置中心获取
        List<QuickActionDTO> quickActions = new ArrayList<>();

        QuickActionDTO action1 = new QuickActionDTO();
        action1.setId(1L);
        action1.setName("景点搜索");
        action1.setIconUrl("https://example.com/icons/search.png");
        action1.setLinkUrl("/pages/search/search");
        action1.setBackgroundColor("#4CAF50");
        action1.setSortOrder(1);
        action1.setEnabled(true);
        quickActions.add(action1);

        QuickActionDTO action2 = new QuickActionDTO();
        action2.setId(2L);
        action2.setName("附近景点");
        action2.setIconUrl("https://example.com/icons/location.png");
        action2.setLinkUrl("/pages/nearby/nearby");
        action2.setBackgroundColor("#2196F3");
        action2.setSortOrder(2);
        action2.setEnabled(true);
        quickActions.add(action2);

        QuickActionDTO action3 = new QuickActionDTO();
        action3.setId(3L);
        action3.setName("我的收藏");
        action3.setIconUrl("https://example.com/icons/favorite.png");
        action3.setLinkUrl("/pages/favorite/favorite");
        action3.setBackgroundColor("#FF9800");
        action3.setSortOrder(3);
        action3.setEnabled(true);
        quickActions.add(action3);

        QuickActionDTO action4 = new QuickActionDTO();
        action4.setId(4L);
        action4.setName("行程规划");
        action4.setIconUrl("https://example.com/icons/plan.png");
        action4.setLinkUrl("/pages/plan/plan");
        action4.setBackgroundColor("#9C27B0");
        action4.setSortOrder(4);
        action4.setEnabled(true);
        quickActions.add(action4);

        return quickActions;
    }

    @Override
    public HomeStats getHomeStats() {
        log.debug("获取首页统计数据");

        HomeStats stats = new HomeStats();

        try {
            // 获取景点总数
            QueryWrapper<ScenicSpot> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_deleted", 0)
                       .eq("status", 1);
            Long totalSpots = scenicSpotRepository.selectCount(queryWrapper);
            stats.setTotalSpots(totalSpots != null ? totalSpots.intValue() : 0);

            // 这里暂时使用模拟数据，后期需要从用户服务获取
            stats.setTotalUsers(1000); // 模拟用户总数
            stats.setTodayVisitors(150); // 模拟今日访客
            stats.setTotalFavorites(5000); // 模拟总收藏数

        } catch (Exception e) {
            log.error("获取首页统计数据失败", e);
            // 返回默认值
            stats.setTotalSpots(0);
            stats.setTotalUsers(0);
            stats.setTodayVisitors(0);
            stats.setTotalFavorites(0);
        }

        return stats;
    }
}