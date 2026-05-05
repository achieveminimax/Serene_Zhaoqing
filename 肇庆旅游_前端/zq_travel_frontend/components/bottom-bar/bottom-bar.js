Component({
  properties: {
    show: {
      type: Boolean,
      value: true
    },
    activeIndex: {
      type: Number,
      value: 0
    }
  },

  data: {
    tabs: [
      { name: '首页', icon: '🏠', path: '/pages/index/index' },
      { name: '音乐', icon: '📖', path: '/pages/music/music' },
      { name: '我的', icon: '👤', path: '/pages/mine/mine' }
    ]
  },

  methods: {
    onTabTap(e) {
      const index = parseInt(e.currentTarget.dataset.index);
      const tab = this.data.tabs[index];
      
      // 触发切换事件
      this.triggerEvent('tabChange', { 
        index: index,
        name: tab.name,
        path: tab.path
      });
      
      // 如果索引不同，则跳转页面
      if (index !== this.data.activeIndex) {
        wx.switchTab({
          url: tab.path
        });
      }
    }
  }
});
