Page({
  data: {
    // 轮播图数据
    bannerList: [
      {
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
        title: '森林呼吸冥想',
        tag: '精选推荐'
      },
      {
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
        title: '翡翠池共鸣',
        tag: '水疗之声'
      },
      {
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
        title: '山间禅意',
        tag: '深度疗愈'
      }
    ],
    currentBannerIndex: 0,

    // 推荐列表数据
    recommendList: [
      {
        id: 1,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/2dbf1fe2a898e2607c52d27538ff7c89.jpg',
        title: '数字排毒之旅',
        description: '4个阶段 · 12个课程',
        progress: 65
      },
      {
        id: 2,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg',
        title: '正念清晨',
        description: '每日 · 15分钟',
        action: '继续'
      }
    ],

    // 景点推荐数据
    scenicList: [
      {
        name: '星湖湿地公园',
        subtitle: '候鸟天堂 · 生态氧吧',
        emoji: '🦢'
      },
      {
        name: '紫云谷',
        subtitle: '天然氧吧 · 溯溪探险',
        emoji: '🌊'
      },
      {
        name: '砚阳湖公园',
        subtitle: '城市绿肺 · 休闲胜地',
        emoji: '🌸'
      },
      {
        name: '九龙湖',
        subtitle: '湖光山色 · 森林秘境',
        emoji: '🏞️'
      }
    ]
  },

  onLoad: function() {
    // 页面加载时执行
  },

  onShow: function() {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 0
      });
    }
  },

  // 搜索框点击
  onSearchTap: function() {
    wx.navigateTo({
      url: '/pages/search/search'
    });
  },

  // 轮播图切换
  onBannerChange: function(e) {
    this.setData({
      currentBannerIndex: e.detail.current
    });
  },

  // 点击指示器切换轮播
  onDotTap: function(e) {
    const index = e.currentTarget.dataset.index;
    this.setData({
      currentBannerIndex: index
    });
  },

  // 卡片点击
  onCardTap: function(e) {
    const id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/detail/detail?id=' + id
    });
  },

  // 景点点击
  onScenicTap: function(e) {
    const index = e.currentTarget.dataset.index;
    wx.navigateTo({
      url: '/pages/scenic-detail/scenic-detail?index=' + index
    });
  },

  // 悬浮按钮点击 - 进入AI对话
  onFabMainTap: function() {
    wx.navigateTo({
      url: '/pages/ai-agent/ai-agent'
    });
  }
});
