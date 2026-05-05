Page({
  data: {
    steps: '8,432',
    goal: '10,000',
    progressPercent: 84,
    strokeDashoffset: 85,
    calories: 342,
    distance: '5.8',
    duration: 56,
    weekSteps: [6200, 7800, 8500, 7200, 9100, 8800, 8432],
    weekDays: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    routes: [
      {
        name: '鼎湖山森林步道',
        distance: '4.2 km',
        time: '60 min',
        image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuB1oZfwZ09Bcqu4PoCZlZ1Mf3WT-gksGtF56tEyIkhaN9S6t54Xi-k_5e5Wm0LpuNbnJ3nVPIZlYjZcYkJB7K5Kai0od-QM6nSIC32vc2BBcJaZyK85noSTLEMtMHhHIxf52IlzDu70ZCF9elfp8dylsZ_oWdX85F4SNaszDCPbOb_0l8o2Esau78deOxTZEnZk8qEOn7EZdga4intbDDUQYSuK1pcA7HYaHLmS90G0jc_Y2mddjvObyncSb8gbpGhpGBw6BrAHoGo'
      },
      {
        name: '七星岩湖畔漫步',
        distance: '3.5 km',
        time: '45 min',
        image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBXQMSYn6Q8462W3VBjSCrJstoR18tBIdGrkaR4EMfZW1p-4pdSHxZ6iMm5kIxMXt5g9iLeyDptBd0IxrxCeLL7D3yj_-mGwbxtf7dKDdGTMDA0UaKDNuIEFSn2uHsV6llPJ2V_m_zY9p4I9FRdi9YrDZAjTQjJtycv1O7EB5l8gYkVvhpju6ejO8YfA7FakcVt0z3Nk2aVDlnsWb1iQec8FULFby3Jk13lLWT98GRs_vlT2-jd8qOvmgQx5d3IjESIqui5oLiGeGs'
      }
    ],
    healthData: [
      { icon: '🏃', value: 45, unit: 'mins', label: '活跃时间' },
      { icon: '🌙', value: 85, unit: '%', label: '睡眠质量' },
      { icon: '⛰️', value: 120, unit: 'm', label: '海拔增益' }
    ]
  },

  onLoad: function(options) {
    this.calculateProgress();
    if (options.steps) {
      this.setData({
        steps: options.steps
      });
      this.calculateProgress();
    }
  },

  // 计算 SVG 环形进度
  calculateProgress: function() {
    const stepsNum = parseInt(this.data.steps.replace(/,/g, ''));
    const goalNum = parseInt(this.data.goal.replace(/,/g, ''));
    const progress = Math.min(stepsNum / goalNum, 1);
    const progressPercent = Math.round(progress * 100);
    
    // SVG 圆周长 = 2 * PI * r = 2 * 3.14159 * 85 ≈ 534
    const circumference = 534;
    const strokeDashoffset = circumference * (1 - progress);
    
    this.setData({
      progressPercent: progressPercent,
      strokeDashoffset: strokeDashoffset
    });
  },

  onBack: function() {
    wx.navigateBack();
  },

  onMoreTap: function() {
    wx.showActionSheet({
      itemList: ['查看历史记录', '设置目标', '分享数据'],
      success: function(res) {
        wx.showToast({
          title: '功能开发中',
          icon: 'none'
        });
      }
    });
  },

  onRouteTap: function(e) {
    const index = e.currentTarget.dataset.index;
    const route = this.data.routes[index];
    wx.showToast({
      title: route.name,
      icon: 'none'
    });
  }
});
