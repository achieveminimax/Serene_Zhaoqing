Page({
  data: {
    currentHeartRate: 72,
    isMeasuring: false,
    measureTime: '',
    heartRateStatus: {
      text: '正常',
      class: 'normal'
    },
    todayStats: {
      min: 62,
      avg: 74,
      max: 104,
      count: 12
    },
    historyList: []
  },

  onLoad() {
    this.updateMeasureTime();
    this.generateHistoryData();
  },

  onShow() {
    this.updateMeasureTime();
  },

  // 更新测量时间
  updateMeasureTime() {
    const now = new Date();
    const timeStr = `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
    this.setData({
      measureTime: timeStr
    });
  },

  // 生成历史数据
  generateHistoryData() {
    const history = [];
    const descriptions = ['散步后', '休息时', '工作时', '午餐后', '冥想后'];
    
    for (let i = 0; i < 3; i++) {
      const time = new Date(Date.now() - i * 2 * 60 * 60 * 1000);
      const value = Math.floor(Math.random() * 20) + 65;
      
      history.push({
        time: `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')}`,
        value: value,
        desc: descriptions[i % descriptions.length]
      });
    }

    this.setData({
      historyList: history
    });
  },

  // 返回按钮
  onBackTap() {
    wx.navigateBack();
  },

  // 开始/停止测量
  onMeasureTap() {
    const isMeasuring = !this.data.isMeasuring;
    this.setData({ isMeasuring });

    if (isMeasuring) {
      // 模拟测量过程
      let count = 0;
      this.measureInterval = setInterval(() => {
        count++;
        // 模拟心率波动
        const newRate = Math.floor(Math.random() * 20) + 65;
        this.setData({ currentHeartRate: newRate });
        this.updateHeartRateStatus(newRate);

        // 5秒后自动停止
        if (count >= 10) {
          this.stopMeasuring();
        }
      }, 500);
    } else {
      this.stopMeasuring();
    }
  },

  stopMeasuring() {
    if (this.measureInterval) {
      clearInterval(this.measureInterval);
      this.measureInterval = null;
    }
    this.setData({ isMeasuring: false });
    this.updateMeasureTime();

    wx.showToast({
      title: '测量完成',
      icon: 'success'
    });
  },

  // 更新心率状态
  updateHeartRateStatus(rate) {
    let status = { text: '正常', class: 'normal' };

    if (rate > 100) {
      status = { text: '过快', class: 'danger' };
    } else if (rate > 90) {
      status = { text: '偏快', class: 'warning' };
    } else if (rate < 60) {
      status = { text: '偏慢', class: 'warning' };
    }

    this.setData({ heartRateStatus: status });
  },

  // 开始呼吸练习
  onStartBreathing() {
    wx.showToast({
      title: '呼吸练习开发中',
      icon: 'none'
    });
  },

  // 查看更多历史
  onViewMoreHistory() {
    wx.showToast({
      title: '功能开发中',
      icon: 'none'
    });
  },

  onUnload() {
    if (this.measureInterval) {
      clearInterval(this.measureInterval);
    }
  }
});
