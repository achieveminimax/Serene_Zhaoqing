Page({
  data: {
    searchValue: '',
    currentTab: 0,
    tabs: ['推荐', '音乐', '播客', '冥想', '助眠'],
    currentIndex: -1,
    isPlaying: false,
    isFavorite: false,
    meditationIndex: -1,
    isMeditationPlaying: false,
    progress: 0,
    currentTime: 0,
    duration: 240,
    
    // 沉浸式播放器
    showImmersivePlayer: false,
    
    // 悬浮按钮
    fabExpanded: false,
    fabActions: [
      { icon: 'heart', text: '收藏' },
      { icon: 'share', text: '分享' },
      { icon: 'download', text: '下载' },
      { icon: 'list', text: '列表' }
    ],
    
    // 底部操作栏
    showBottomBar: true,
    bottomBarActions: [
      { icon: 'heart', highlight: false },
      { icon: 'play', highlight: true },
      { icon: 'more' }
    ],
    quickActions: [
      { icon: 'music', text: '音乐' },
      { icon: 'volume', text: '白噪音' },
      { icon: 'user', text: '我的' }
    ],

    recommendList: [
      {
        id: 1,
        emoji: '🌸',
        title: '春日治愈风景精选',
        tag: '根据常听推荐',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c280ee9ee8f901a2818fbf0292f0089b.jpg'
      },
      {
        id: 2,
        emoji: '🌧️',
        title: '自然白噪音助眠',
        tag: '十万收藏',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/fb73c05c44b8aee5d6e20c5f8a144b74.jpg'
      },
      {
        id: 3,
        emoji: '🧘',
        title: '情绪疗愈冥想指南',
        tag: '',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/676d38d42b141762b69865ffc7075b47.jpg'
      },
      {
        id: 4,
        emoji: '🏔️',
        title: '森林氧吧深呼吸',
        tag: '超5万人收藏',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/167f5524b00d310b21d88fb55c7566c9.jpg'
      },
      {
        id: 5,
        emoji: '🌙',
        title: '睡前放松轻音乐',
        tag: '',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/2dbf1fe2a898e2607c52d27538ff7c89.jpg'
      }
    ],

    relaxMusic: [
      {
        name: '森林鸟叫',
        artist: '自然音效',
        emoji: '🌲',
        tag: 'SQ',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/167f5524b00d310b21d88fb55c7566c9.jpg'
      },
      {
        name: '水流声',
        artist: '自然音效',
        emoji: '🌊',
        tag: 'SQ',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/1cf08b186d2002ab5284a6551eace2d2.jpg'
      },
      {
        name: '海浪翻滚',
        artist: '海洋声音',
        emoji: '🐦',
        tag: 'Hi-Res',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/2dbf1fe2a898e2607c52d27538ff7c89.jpg'
      },
      {
        name: '清晨鸟叫',
        artist: '森林音乐',
        emoji: '♨️',
        tag: '',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/676d38d42b141762b69865ffc7075b47.jpg'
      },
      {
        name: '细细雨声',
        artist: '东方禅意',
        emoji: '🎋',
        tag: 'SQ',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/a5f314ac8ba95d39e8d1ae53cbe29805.jpg'
      },
      {
        name: '蛐蛐声',
        artist: '疗愈音乐',
        emoji: '🕯️',
        tag: '',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/beab841233a3117eb22b97c533f32bd0.jpg'
      },
      {
        name: '鸟叫声',
        artist: '轻爵士',
        emoji: '🌲',
        tag: '',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg'
      }
    ],

    natureSounds: [
      {
        emoji: '🌧️',
        name: '细雨绵绵',
        desc: '适合阅读·专注',
        image: ''
      },
      {
        emoji: '⛈️',
        name: '雷雨交加',
        desc: '深度助眠·放松',
        image: ''
      },
      {
        emoji: '🌊',
        name: '海浪拍岸',
        desc: '冥想·减压',
        image: ''
      },
      {
        emoji: '🍃',
        name: '竹林风声',
        desc: '禅意·静心',
        image: ''
      }
    ],

    meditationMusic: [
      {
        name: '晨间冥想引导',
        emoji: '🌅',
        duration: '15分钟',
        category: '正念冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/1cf08b186d2002ab5284a6551eace2d2.jpg'
      },
      {
        name: '深度放松身体扫描',
        emoji: '🧘',
        duration: '20分钟',
        category: '放松冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/a5f314ac8ba95d39e8d1ae53cbe29805.jpg'
      },
      {
        name: '睡前感恩练习',
        emoji: '🙏',
        duration: '10分钟',
        category: '睡前冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/beab841233a3117eb22b97c533f32bd0.jpg'
      },
      {
        name: '呼吸调节练习',
        emoji: '💨',
        duration: '5分钟',
        category: '呼吸练习',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg'
      }
    ]
  },

  onLoad: function(options) {
    this.initNatureSoundsImages();
    this.startProgressTimer();
  },

  onShow: function() {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1
      });
    }
  },

  initNatureSoundsImages: function() {
    const audioImages = [
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/167f5524b00d310b21d88fb55c7566c9.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/1cf08b186d2002ab5284a6551eace2d2.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/2dbf1fe2a898e2607c52d27538ff7c89.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/676d38d42b141762b69865ffc7075b47.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/a5f314ac8ba95d39e8d1ae53cbe29805.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/beab841233a3117eb22b97c533f32bd0.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c280ee9ee8f901a2818fbf0292f0089b.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/fb73c05c44b8aee5d6e20c5f8a144b74.jpg'
    ];
    const bannerImages = [
      'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
      'https://gitee.com/su_feng_L/picture/raw/main/轮播图/9b55667e92fae34f0f60423416d3a0ef.jpg'
    ];
    const allImages = [...audioImages, ...bannerImages];

    const getRandomImage = () => {
      const randomIndex = Math.floor(Math.random() * allImages.length);
      return allImages[randomIndex];
    };

    const natureSounds = this.data.natureSounds.map(item => ({
      ...item,
      image: getRandomImage()
    }));

    this.setData({
      natureSounds: natureSounds
    });
  },

  onSearchInput: function(e) {
    this.setData({
      searchValue: e.detail.value
    });
  },

  onVoiceSearch: function() {
    wx.showToast({
      title: '语音搜索功能开发中',
      icon: 'none',
      duration: 1500
    });
  },

  onTabChange: function(e) {
    const index = parseInt(e.currentTarget.dataset.index);
    this.setData({
      currentTab: index
    });
  },

  onPlaylistTap: function(e) {
    const id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/playlist-detail/playlist-detail?id=' + id
    });
  },

  onMusicTap: function(e) {
    const index = e.currentTarget.dataset.index;
    const currentIndex = this.data.currentIndex;
    const isPlaying = this.data.isPlaying;

    if (currentIndex === index && isPlaying) {
      this.pauseMusic();
    } else {
      this.playMusic(index);
    }
  },

  playMusic: function(index) {
    const music = this.data.relaxMusic[index];
    this.setData({
      currentIndex: index,
      isPlaying: true,
      duration: 180 + Math.floor(Math.random() * 120),
      currentTime: 0,
      progress: 0
    });

    // 跳转到播放器页面
    wx.navigateTo({
      url: '/pages/player/player?index=' + index + '&from=relax'
    });
  },

  pauseMusic: function() {
    this.setData({
      isPlaying: false
    });
  },

  togglePlay: function() {
    if (this.data.currentIndex < 0) {
      wx.showToast({
        title: '请先选择音乐',
        icon: 'none',
        duration: 1500
      });
      return;
    }

    const isPlaying = !this.data.isPlaying;
    this.setData({
      isPlaying: isPlaying
    });

    wx.showToast({
      title: isPlaying ? '继续播放' : '已暂停',
      icon: 'none',
      duration: 1000
    });
  },

  toggleFavorite: function() {
    const isFavorite = !this.data.isFavorite;
    this.setData({
      isFavorite: isFavorite
    });

    wx.showToast({
      title: isFavorite ? '已收藏到喜欢' : '已取消收藏',
      icon: 'none',
      duration: 1000
    });
  },

  showPlaylist: function() {
    wx.showToast({
      title: '播放列表功能开发中',
      icon: 'none',
      duration: 1500
    });
  },

  onNatureTap: function(e) {
    const index = e.currentTarget.dataset.index;
    const sound = this.data.natureSounds[index];

    wx.showToast({
      title: '正在播放: ' + sound.name,
      icon: 'none',
      duration: 1500
    });
  },

  onMeditationTap: function(e) {
    const index = e.currentTarget.dataset.index;
    const currentIndex = this.data.meditationIndex;
    const isMeditationPlaying = this.data.isMeditationPlaying;

    if (currentIndex === index && isMeditationPlaying) {
      this.setData({
        isMeditationPlaying: false
      });
      wx.showToast({
        title: '冥想已暂停',
        icon: 'none',
        duration: 1000
      });
    } else {
      this.setData({
        meditationIndex: index,
        isMeditationPlaying: true
      });
      wx.showToast({
        title: '正在播放: ' + this.data.meditationMusic[index].name,
        icon: 'none',
        duration: 1500
      });
    }
  },

  onSliderChange: function(e) {
    const value = e.detail.value;
    const duration = this.data.duration;
    const newTime = Math.floor((value / 100) * duration);

    this.setData({
      currentTime: newTime,
      progress: value
    });
  },

  onSliderChanging: function(e) {
    const value = e.detail.value;
    const duration = this.data.duration;
    const newTime = Math.floor((value / 100) * duration);

    this.setData({
      currentTime: newTime,
      progress: value
    });
  },

  startProgressTimer: function() {
    this.progressTimer = setInterval(() => {
      if (this.data.isPlaying && this.data.currentIndex >= 0) {
        let currentTime = this.data.currentTime + 1;
        let progress = (currentTime / this.data.duration) * 100;

        if (progress >= 100) {
          this.nextMusic();
        } else {
          this.setData({
            currentTime: currentTime,
            progress: progress
          });
        }
      }
    }, 1000);
  },

  nextMusic: function() {
    let currentIndex = this.data.currentIndex;
    let newIndex = currentIndex + 1;

    if (newIndex >= this.data.relaxMusic.length) {
      newIndex = 0;
    }

    this.playMusic(newIndex);
  },

  formatTime: function(seconds) {
    if (!seconds || isNaN(seconds)) {
      return '00:00';
    }

    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);

    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  },

  onUnload: function() {
    if (this.progressTimer) {
      clearInterval(this.progressTimer);
    }
  },

  // 沉浸式播放器控制
  showImmersivePlayer: function() {
    this.setData({
      showImmersivePlayer: true
    });
  },

  hideImmersivePlayer: function() {
    this.setData({
      showImmersivePlayer: false
    });
  },

  // 悬浮按钮控制
  onFabMainTap: function() {
    this.setData({
      fabExpanded: !this.data.fabExpanded
    });
  },

  onFabActionTap: function(e) {
    const index = e.detail.index;
    const action = this.data.fabActions[index];
    wx.showToast({
      title: action.text,
      icon: 'none'
    });
    this.setData({
      fabExpanded: false
    });
  },

  // 底部操作栏控制
  onBottomBarAction: function(e) {
    const index = e.detail.index;
    const actions = ['收藏', '播放/暂停', '更多'];
    wx.showToast({
      title: actions[index],
      icon: 'none'
    });
  },

  onQuickAction: function(e) {
    const index = e.detail.index;
    const quicks = ['音乐', '白噪音', '我的'];
    wx.showToast({
      title: quicks[index],
      icon: 'none'
    });
  },

  // 播放控制
  playPrev: function() {
    let currentIndex = this.data.currentIndex;
    let newIndex = currentIndex - 1;
    if (newIndex < 0) {
      newIndex = this.data.relaxMusic.length - 1;
    }
    this.playMusic(newIndex);
  },

  playNext: function() {
    let currentIndex = this.data.currentIndex;
    let newIndex = currentIndex + 1;
    if (newIndex >= this.data.relaxMusic.length) {
      newIndex = 0;
    }
    this.playMusic(newIndex);
  }
});
