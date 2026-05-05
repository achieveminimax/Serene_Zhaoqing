Page({
  data: {
    // 疗愈食谱数据
    recipeList: [
      {
        name: '晨间禅意汤',
        description: '排毒养颜',
        time: '5分钟',
        level: '简单',
        tag1: '排毒',
        recipeImage: 'https://gitee.com/su_feng_L/picture/raw/main/recipe/153ba40234b0e76f2310cc94687019db.jpg'
      },
      {
        name: ' serenity 谷物碗',
        description: '安神助眠',
        time: '15分钟',
        level: '中等',
        tag1: '安神',
        recipeImage: 'https://gitee.com/su_feng_L/picture/raw/main/recipe/0c8a7c8723bf9808521d72e028cc5eba.jpg'
      },
      {
        name: '红枣枸杞茶',
        description: '补气养血',
        time: '10分钟',
        level: '简单',
        tag1: '养生',
        recipeImage: 'https://gitee.com/su_feng_L/picture/raw/main/recipe/a0f3073aa994362e3ad53975c16f3223.jpg'
      }
    ]
  },

  onLoad: function(options) {
  },

  onShow: function() {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 2
      });
    }
  },

  // 设置项点击
  onSettingsTap: function(e) {
    const type = e.currentTarget.dataset.type;
    const titles = {
      account: '账户管理',
      privacy: '隐私与安全',
      support: '帮助中心'
    };
    wx.showToast({
      title: titles[type] + '开发中',
      icon: 'none',
      duration: 1500
    });
  },

  // 食谱点击
  onRecipeTap: function(e) {
    const index = e.currentTarget.dataset.index;
    wx.showToast({
      title: '食谱详情开发中',
      icon: 'none'
    });
  },

  // 商店点击 - 跳转到商店详情页
  onShopTap: function() {
    wx.navigateTo({
      url: '/pages/shop-detail/shop-detail'
    });
  },

  // 心率点击 - 跳转到心率详情页
  onHeartRateTap: function() {
    wx.navigateTo({
      url: '/pages/heart-rate-detail/heart-rate-detail'
    });
  }
});
