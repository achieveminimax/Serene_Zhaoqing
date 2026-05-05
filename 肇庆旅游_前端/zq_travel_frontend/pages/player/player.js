Page({
  data: {
    // 当前播放音乐信息
    currentMusic: null,
    currentIndex: 0,

    // 播放状态
    isPlaying: false,
    isFavorite: false,
    playMode: 'list', // list, random, single

    // 播放进度
    currentTime: 0,
    duration: 240,
    progress: 0,

    // 播放列表
    playList: [],

    // 显示播放列表
    showPlayList: false,

    // 歌词显示
    showLyrics: false,

    // 背景模糊图
    backgroundImage: '',

    // 定时器
    showTimerModal: false,
    timerValue: 0,
    timerRemaining: 0,
    timerInterval: null
  },

  onLoad: function(options) {
    // 获取传入的音乐数据
    const { index, from } = options;

    // 从本地存储或全局数据获取播放列表
    const app = getApp();
    let playList = [];

    if (from === 'relax') {
      playList = app.globalData.relaxMusic || this.getDefaultRelaxMusic();
    } else if (from === 'meditation') {
      playList = app.globalData.meditationMusic || this.getDefaultMeditationMusic();
    } else {
      playList = this.getDefaultRelaxMusic();
    }

    const currentIndex = parseInt(index) || 0;
    const currentMusic = playList[currentIndex];

    this.setData({
      playList,
      currentIndex,
      currentMusic,
      duration: currentMusic.duration || 240,
      backgroundImage: currentMusic.image || ''
    });

    // 自动开始播放
    this.playMusic();

    // 启动进度计时器
    this.startProgressTimer();
  },

  getDefaultRelaxMusic: function() {
    return [
      {
        name: '森林鸟叫',
        artist: '自然音效',
        emoji: '🌲',
        tag: 'SQ',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/167f5524b00d310b21d88fb55c7566c9.jpg',
        lyrics: ['🎵 森林鸟叫', '', '清晨的森林，鸟儿在歌唱...', '', '让大自然的声音治愈你的心灵']
      },
      {
        name: '水流声',
        artist: '自然音效',
        emoji: '🌊',
        tag: 'SQ',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/1cf08b186d2002ab5284a6551eace2d2.jpg',
        lyrics: ['🎵 水流声', '', '潺潺流水，清澈见底...', '', '让水声带走你的烦恼']
      },
      {
        name: '海浪翻滚',
        artist: '海洋声音',
        emoji: '🐦',
        tag: 'Hi-Res',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/2dbf1fe2a898e2607c52d27538ff7c89.jpg',
        lyrics: ['🎵 海浪翻滚', '', '海浪拍打着沙滩...', '', '感受大海的广阔与宁静']
      },
      {
        name: '清晨鸟叫',
        artist: '森林音乐',
        emoji: '♨️',
        tag: '',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/676d38d42b141762b69865ffc7075b47.jpg',
        lyrics: ['🎵 清晨鸟叫', '', '新的一天开始了...', '', '鸟儿在窗外欢快地歌唱']
      },
      {
        name: '细细雨声',
        artist: '东方禅意',
        emoji: '🎋',
        tag: 'SQ',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/a5f314ac8ba95d39e8d1ae53cbe29805.jpg',
        lyrics: ['🎵 细细雨声', '', '雨滴轻轻地落下...', '', '听雨是一种享受']
      },
      {
        name: '蛐蛐声',
        artist: '疗愈音乐',
        emoji: '🕯️',
        tag: '',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/beab841233a3117eb22b97c533f32bd0.jpg',
        lyrics: ['🎵 蛐蛐声', '', '夏夜的蟋蟀在歌唱...', '', '带你回到童年的夏夜']
      },
      {
        name: '鸟叫声',
        artist: '轻爵士',
        emoji: '🌲',
        tag: '',
        duration: 300,
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg',
        lyrics: ['🎵 鸟叫声', '', '鸟儿们在树上欢唱...', '', '感受生命的活力']
      }
    ];
  },

  getDefaultMeditationMusic: function() {
    return [
      {
        name: '晨间冥想引导',
        emoji: '🌅',
        duration: 900,
        category: '正念冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/1cf08b186d2002ab5284a6551eace2d2.jpg',
        lyrics: ['🧘 晨间冥想', '', '深呼吸，放松你的身体...']
      },
      {
        name: '深度放松身体扫描',
        emoji: '🧘',
        duration: 1200,
        category: '放松冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/a5f314ac8ba95d39e8d1ae53cbe29805.jpg',
        lyrics: ['🧘 身体扫描', '', '从头到脚，感受每一个部位...']
      },
      {
        name: '睡前感恩练习',
        emoji: '🙏',
        duration: 600,
        category: '睡前冥想',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/beab841233a3117eb22b97c533f32bd0.jpg',
        lyrics: ['🧘 感恩冥想', '', '回想今天值得感恩的事...']
      },
      {
        name: '呼吸调节练习',
        emoji: '💨',
        duration: 300,
        category: '呼吸练习',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/音频/音频配图/c190005f2dec85478f848bd09f382cb5.jpg',
        lyrics: ['🧘 呼吸练习', '', '吸气...呼气...保持节奏...']
      }
    ];
  },

  // 播放/暂停
  togglePlay: function() {
    if (this.data.isPlaying) {
      this.pauseMusic();
    } else {
      this.playMusic();
    }
  },

  playMusic: function() {
    this.setData({ isPlaying: true });
  },

  pauseMusic: function() {
    this.setData({ isPlaying: false });
  },

  // 上一首
  playPrev: function() {
    let currentIndex = this.data.currentIndex;
    let newIndex = currentIndex - 1;

    if (newIndex < 0) {
      newIndex = this.data.playList.length - 1;
    }

    this.switchMusic(newIndex);
  },

  // 下一首
  playNext: function() {
    let currentIndex = this.data.currentIndex;
    let newIndex;

    if (this.data.playMode === 'random') {
      newIndex = Math.floor(Math.random() * this.data.playList.length);
    } else {
      newIndex = currentIndex + 1;
      if (newIndex >= this.data.playList.length) {
        newIndex = 0;
      }
    }

    this.switchMusic(newIndex);
  },

  // 切换音乐
  switchMusic: function(index) {
    const music = this.data.playList[index];
    this.setData({
      currentIndex: index,
      currentMusic: music,
      currentTime: 0,
      progress: 0,
      duration: music.duration || 240,
      backgroundImage: music.image || '',
      isPlaying: true
    });
  },

  // 进度条控制
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

  // 进度计时器
  startProgressTimer: function() {
    this.progressTimer = setInterval(() => {
      if (this.data.isPlaying) {
        let currentTime = this.data.currentTime + 1;
        let progress = (currentTime / this.data.duration) * 100;

        if (progress >= 100) {
          if (this.data.playMode === 'single') {
            // 单曲循环
            currentTime = 0;
            progress = 0;
          } else {
            // 播放下一首
            this.playNext();
            return;
          }
        }

        this.setData({
          currentTime: currentTime,
          progress: progress
        });
      }
    }, 1000);
  },

  // 切换播放模式
  togglePlayMode: function() {
    const modes = ['list', 'random', 'single'];
    const currentMode = this.data.playMode;
    const currentIndex = modes.indexOf(currentMode);
    const nextIndex = (currentIndex + 1) % modes.length;
    const nextMode = modes[nextIndex];

    const modeText = {
      'list': '列表循环',
      'random': '随机播放',
      'single': '单曲循环'
    };

    this.setData({ playMode: nextMode });

    wx.showToast({
      title: modeText[nextMode],
      icon: 'none'
    });
  },

  // 收藏
  toggleFavorite: function() {
    this.setData({
      isFavorite: !this.data.isFavorite
    });

    wx.showToast({
      title: this.data.isFavorite ? '已收藏' : '取消收藏',
      icon: 'none'
    });
  },

  // 显示/隐藏播放列表
  togglePlayList: function() {
    this.setData({
      showPlayList: !this.data.showPlayList
    });
  },

  // 选择播放列表中的音乐
  selectMusic: function(e) {
    const index = e.currentTarget.dataset.index;
    this.switchMusic(index);
    this.setData({ showPlayList: false });
  },

  // 显示/隐藏歌词
  toggleLyrics: function() {
    this.setData({
      showLyrics: !this.data.showLyrics
    });
  },

  // 显示定时器弹窗
  showTimer: function() {
    this.setData({
      showTimerModal: true
    });
  },

  // 隐藏定时器弹窗
  hideTimer: function() {
    this.setData({
      showTimerModal: false
    });
  },

  // 设置定时器
  setTimer: function(e) {
    const value = parseInt(e.currentTarget.dataset.value);

    // 清除之前的定时器
    if (this.data.timerInterval) {
      clearInterval(this.data.timerInterval);
    }

    this.setData({
      timerValue: value,
      timerRemaining: value * 60,
      showTimerModal: false
    });

    if (value > 0) {
      wx.showToast({
        title: `已设置 ${value} 分钟后关闭`,
        icon: 'none'
      });

      // 启动定时器倒计时
      const timerInterval = setInterval(() => {
        let remaining = this.data.timerRemaining - 1;

        if (remaining <= 0) {
          // 时间到，停止播放
          clearInterval(timerInterval);
          this.pauseMusic();
          wx.showToast({
            title: '定时关闭已生效',
            icon: 'none'
          });
          this.setData({
            timerValue: 0,
            timerRemaining: 0,
            timerInterval: null
          });
        } else {
          this.setData({ timerRemaining: remaining });
        }
      }, 1000);

      this.setData({ timerInterval });
    } else {
      wx.showToast({
        title: '已取消定时关闭',
        icon: 'none'
      });
    }
  },

  // 返回上一页
  onBack: function() {
    wx.navigateBack();
  },

  // 分享
  onShare: function() {
    wx.showShareMenu({
      withShareTicket: true,
      menus: ['shareAppMessage', 'shareTimeline']
    });
  },

  // 格式化时间
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
    if (this.data.timerInterval) {
      clearInterval(this.data.timerInterval);
    }
  }
});
