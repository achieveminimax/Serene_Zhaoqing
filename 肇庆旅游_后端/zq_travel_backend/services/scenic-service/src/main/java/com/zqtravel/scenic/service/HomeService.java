package com.zqtravel.scenic.service;

import com.zqtravel.scenic.dto.response.BannerDTO;
import com.zqtravel.scenic.dto.response.QuickActionDTO;
import com.zqtravel.scenic.dto.response.RecommendDTO;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;

import java.util.List;

/**
 * 首页服务接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface HomeService {

    /**
     * 获取轮播图列表
     *
     * @return 轮播图列表
     */
    List<BannerDTO> getBanners();

    /**
     * 获取首页推荐内容
     *
     * @return 推荐内容列表
     */
    List<RecommendDTO> getHomeRecommends();

    /**
     * 获取首页景点推荐
     *
     * @param limit 限制数量
     * @return 景点推荐列表
     */
    List<ScenicSpotDTO> getScenicListForHome(Integer limit);

    /**
     * 获取快捷操作入口
     *
     * @return 快捷操作入口列表
     */
    List<QuickActionDTO> getQuickActions();

    /**
     * 获取首页统计数据
     *
     * @return 统计数据
     */
    HomeStats getHomeStats();

    /**
     * 首页统计数据类
     */
    class HomeStats {
        private Integer totalSpots;
        private Integer totalUsers;
        private Integer todayVisitors;
        private Integer totalFavorites;

        // getters and setters
        public Integer getTotalSpots() {
            return totalSpots;
        }

        public void setTotalSpots(Integer totalSpots) {
            this.totalSpots = totalSpots;
        }

        public Integer getTotalUsers() {
            return totalUsers;
        }

        public void setTotalUsers(Integer totalUsers) {
            this.totalUsers = totalUsers;
        }

        public Integer getTodayVisitors() {
            return todayVisitors;
        }

        public void setTodayVisitors(Integer todayVisitors) {
            this.todayVisitors = todayVisitors;
        }

        public Integer getTotalFavorites() {
            return totalFavorites;
        }

        public void setTotalFavorites(Integer totalFavorites) {
            this.totalFavorites = totalFavorites;
        }
    }
}