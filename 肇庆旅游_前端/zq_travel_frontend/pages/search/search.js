Page({
  data: {
    searchText: '',
    recentSearches: [],
    trendingSearches: [
      { text: '鼎湖山冥想', trending: true, bgClass: 'bg-secondary text-on-secondary' },
      { text: '雨声白噪音', trending: false, bgClass: 'bg-primary text-on-primary' },
      { text: '颂钵疗愈', trending: false, bgClass: 'bg-tertiary text-on-tertiary' },
      { text: '焦虑缓解', trending: false, bgClass: 'bg-surface text-on-surface' }
    ],
    suggestedItems: [
      {
        id: 1,
        title: '鼎湖山森林冥想',
        desc: '聆听自然的声音，重塑内在平衡',
        tag: '专题课程',
        image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBC_UDYt9BWOg1PLpr7cKK2PXeayKxsgc_sgqT3zeo6z9vnM-C7l-h-thDyAl89--8eRdgnnth5M_AoggVtOSCVfgPsHY1PneGxSPJ1JwVCNmeWezA22_65MCCHUcvGzto8521Cgom9mX92xxitPvEFrrFIBnObv8cBzj6-FhcQrBdNfJb8tBcTXa7apstkO3bl3R-S_y0gqp_P3p00o6ZMUTN7O1Y_YyPEcFx8egrkcteyjgJwPZK5cCpH6iQaw7Yh-BPNJjhJQAI'
      },
      {
        id: 2,
        title: '雨声白噪音',
        desc: '助眠 · 专注 · 45分钟',
        icon: '💧',
        iconBgClass: 'icon-secondary'
      },
      {
        id: 3,
        title: '呼吸练习',
        desc: '冥想 · 减压 · 10分钟',
        icon: '🧘',
        iconBgClass: 'icon-tertiary'
      }
    ]
  },

  onLoad: function(options) {
    this.loadRecentSearches();
    if (options.query) {
      this.setData({
        searchText: decodeURIComponent(options.query)
      });
    }
  },

  onShow: function() {
    this.loadRecentSearches();
  },

  loadRecentSearches: function() {
    try {
      const history = wx.getStorageSync('searchHistory') || [];
      this.setData({
        recentSearches: history.slice(0, 6)
      });
    } catch (e) {
      console.error('加载搜索历史失败:', e);
    }
  },

  saveSearch: function(text) {
    try {
      let history = wx.getStorageSync('searchHistory') || [];
      history = history.filter(item => item !== text);
      history.unshift(text);
      if (history.length > 10) {
        history = history.slice(0, 10);
      }
      wx.setStorageSync('searchHistory', history);
      this.setData({
        recentSearches: history.slice(0, 6)
      });
    } catch (e) {
      console.error('保存搜索历史失败:', e);
    }
  },

  onSearchInput: function(e) {
    this.setData({
      searchText: e.detail.value
    });
  },

  onClearSearch: function() {
    this.setData({
      searchText: ''
    });
  },

  onSearch: function() {
    const text = this.data.searchText.trim();
    if (!text) return;

    this.saveSearch(text);
    
    wx.showToast({
      title: `搜索: ${text}`,
      icon: 'none'
    });
  },

  onHistoryTap: function(e) {
    const text = e.currentTarget.dataset.text;
    this.setData({
      searchText: text
    });
    this.saveSearch(text);
    
    wx.showToast({
      title: `搜索: ${text}`,
      icon: 'none'
    });
  },

  onDeleteHistory: function(e) {
    const index = e.currentTarget.dataset.index;
    try {
      let history = wx.getStorageSync('searchHistory') || [];
      history.splice(index, 1);
      wx.setStorageSync('searchHistory', history);
      this.setData({
        recentSearches: history.slice(0, 6)
      });
    } catch (e) {
      console.error('删除搜索历史失败:', e);
    }
  },

  onClearHistory: function() {
    wx.showModal({
      title: '提示',
      content: '确定要清空所有搜索历史吗？',
      success: (res) => {
        if (res.confirm) {
          try {
            wx.removeStorageSync('searchHistory');
            this.setData({
              recentSearches: []
            });
          } catch (e) {
            console.error('清空搜索历史失败:', e);
          }
        }
      }
    });
  },

  onTrendingTap: function(e) {
    const text = e.currentTarget.dataset.text;
    this.setData({
      searchText: text
    });
    this.saveSearch(text);
    
    wx.showToast({
      title: `搜索: ${text}`,
      icon: 'none'
    });
  },

  onSuggestedTap: function(e) {
    const item = e.currentTarget.dataset.item;
    if (!item) return;

    wx.showToast({
      title: item.title,
      icon: 'none'
    });
  },

  onFilterTap: function() {
    wx.showToast({
      title: '筛选功能',
      icon: 'none'
    });
  },

  onBack: function() {
    wx.navigateBack();
  },

  onNavTap: function(e) {
    const page = e.currentTarget.dataset.page;
    const pages = {
      explore: '/pages/index/index',
      meditation: '/pages/music/music',
      search: '',
      profile: '/pages/mine/mine'
    };

    if (page === 'search') return;

    const url = pages[page];
    if (url) {
      wx.switchTab({
        url: url
      });
    }
  }
});
