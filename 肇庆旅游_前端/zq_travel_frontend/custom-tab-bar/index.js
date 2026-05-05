Component({
  data: {
    selected: 0,
    color: '#065f46',
    selectedColor: '#ffffff'
  },

  methods: {
    switchTab(e) {
      const data = e.currentTarget.dataset;
      const url = data.path;
      
      wx.switchTab({
        url: url
      });
      
      this.setData({
        selected: data.index
      });
    }
  }
});
