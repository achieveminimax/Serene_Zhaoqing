Page({
  data: {
    // 歌单信息
    playlist: {
      id: '',
      title: '',
      description: '',
      cover: '',
      tracks: []
    },
    // 当前播放索引
    currentIndex: -1,
    // 是否正在播放
    isPlaying: false
  },

  onLoad: function(options) {
    // 获取传入的歌单ID
    const { id } = options;

    // 加载歌单数据
    this.loadPlaylistData(id);
  },

  // 加载歌单数据
  loadPlaylistData: function(id) {
    // 这里可以根据ID从服务器或本地存储获取数据
    // 目前使用默认数据演示
    const defaultPlaylist = this.getDefaultPlaylist();

    this.setData({
      playlist: defaultPlaylist
    });

    // 尝试从全局数据获取当前播放状态
    try {
      const app = getApp();
      if (app && app.globalData && app.globalData.currentPlaylist && app.globalData.currentPlaylist.id === id) {
        this.setData({
          currentIndex: app.globalData.currentIndex || -1,
          isPlaying: app.globalData.isPlaying || false
        });
      }
    } catch (e) {
      console.log('获取全局数据失败:', e);
    }
  },

  // 获取默认歌单数据
  getDefaultPlaylist: function() {
    return {
      id: 'morning-mist',
      title: '晨雾旋律',
      description: '精选轻柔钢琴与自然声音，让你以清晰的心境开启新的一天。',
      cover: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBx3xmMyUZoLXPB7GonERMw2WqLq4k1L5yyKy7K3YYHQImu6jzi0jG9ZOdMxqwSoFOEkQgFY4Jfz7UxUf7GRu4gOAIZFBaHMiD64ulwX7azrMH72ycZJF72MWY9ZGwedwMb90HgbSBA5XcOcAEFuFhTGliqybMNC0IbmnGhiBLkozuCtSCYJSNNz05YnBM9DQta8FxxWzW4S1OmVD-_o1IYEMgRGYNore7kLZ0M2iXG_gJrdORXKAdp7fIVcrJvwvPxmWOgE2o4kDQ',
      tracks: [
        {
          id: '1',
          name: '第一缕光',
          artist: '自然疗愈乐团',
          duration: '04:12',
          url: ''
        },
        {
          id: '2',
          name: '松间低语',
          artist: '自然疗愈乐团',
          duration: '03:45',
          url: ''
        },
        {
          id: '3',
          name: '露珠序曲',
          artist: '自然疗愈乐团',
          duration: '05:20',
          url: ''
        },
        {
          id: '4',
          name: '旭日东升',
          artist: '自然疗愈乐团',
          duration: '04:08',
          url: ''
        },
        {
          id: '5',
          name: '山涧微风',
          artist: '自然疗愈乐团',
          duration: '03:52',
          url: ''
        },
        {
          id: '6',
          name: '晨曦静谧',
          artist: '自然疗愈乐团',
          duration: '06:14',
          url: ''
        },
        {
          id: '7',
          name: '雪松倒影',
          artist: '自然疗愈乐团',
          duration: '04:33',
          url: ''
        },
        {
          id: '8',
          name: '幽谷无声',
          artist: '自然疗愈乐团',
          duration: '05:01',
          url: ''
        }
      ]
    };
  },

  // 返回上一页
  onBack: function() {
    wx.navigateBack();
  },

  // 更多操作
  onMore: function() {
    wx.showActionSheet({
      itemList: ['收藏歌单', '分享给朋友', '下载全部'],
      success: (res) => {
        switch (res.tapIndex) {
          case 0:
            wx.showToast({ title: '已收藏歌单', icon: 'success' });
            break;
          case 1:
            this.onShare();
            break;
          case 2:
            wx.showToast({ title: '开始下载', icon: 'loading' });
            break;
        }
      }
    });
  },

  // 分享
  onShare: function() {
    wx.showShareMenu({
      withShareTicket: true,
      menus: ['shareAppMessage', 'shareTimeline']
    });
  },

  // 播放全部
  playAll: function() {
    if (this.data.playlist.tracks.length === 0) {
      wx.showToast({ title: '歌单为空', icon: 'none' });
      return;
    }

    // 从第一首开始播放
    this.playTrack(0);

    wx.showToast({
      title: '开始播放',
      icon: 'none'
    });
  },

  // 随机播放
  shufflePlay: function() {
    if (this.data.playlist.tracks.length === 0) {
      wx.showToast({ title: '歌单为空', icon: 'none' });
      return;
    }

    // 随机选择一首开始播放
    const randomIndex = Math.floor(Math.random() * this.data.playlist.tracks.length);
    this.playTrack(randomIndex, true);

    wx.showToast({
      title: '随机播放',
      icon: 'none'
    });
  },

  // 点击歌曲
  onTrackTap: function(e) {
    const index = e.currentTarget.dataset.index;
    this.playTrack(index);
  },

  // 播放指定歌曲
  playTrack: function(index, isShuffle = false) {
    const track = this.data.playlist.tracks[index];

    // 更新当前播放状态
    this.setData({
      currentIndex: index,
      isPlaying: true
    });

    // 保存到全局数据
    try {
      const app = getApp();
      if (app && app.globalData) {
        app.globalData.currentPlaylist = this.data.playlist;
        app.globalData.currentIndex = index;
        app.globalData.isPlaying = true;
        app.globalData.playMode = isShuffle ? 'random' : 'list';
      }
    } catch (e) {
      console.log('保存全局数据失败:', e);
    }

    // 跳转到播放器页面
    wx.navigateTo({
      url: `/pages/player/player?index=${index}&from=playlist`
    });
  },

  // 歌曲更多操作
  onTrackMore: function(e) {
    const index = e.currentTarget.dataset.index;
    const track = this.data.playlist.tracks[index];

    wx.showActionSheet({
      itemList: ['下一首播放', '添加到歌单', '下载', '分享'],
      success: (res) => {
        switch (res.tapIndex) {
          case 0:
            wx.showToast({ title: '已添加到下一首', icon: 'none' });
            break;
          case 1:
            wx.showToast({ title: '选择歌单', icon: 'none' });
            break;
          case 2:
            wx.showToast({ title: '开始下载', icon: 'loading' });
            break;
          case 3:
            wx.showShareMenu({
              withShareTicket: true,
              menus: ['shareAppMessage']
            });
            break;
        }
      }
    });
  },

  // 用户点击右上角分享
  onShareAppMessage: function() {
    return {
      title: this.data.playlist.title,
      desc: this.data.playlist.description,
      path: `/pages/playlist-detail/playlist-detail?id=${this.data.playlist.id}`,
      imageUrl: this.data.playlist.cover
    };
  },

  // 用户点击右上角分享到朋友圈
  onShareTimeline: function() {
    return {
      title: this.data.playlist.title,
      query: `id=${this.data.playlist.id}`,
      imageUrl: this.data.playlist.cover
    };
  }
});
