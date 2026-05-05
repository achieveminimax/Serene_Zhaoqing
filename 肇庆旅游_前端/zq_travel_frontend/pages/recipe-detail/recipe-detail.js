Page({
  data: {
    id: null,
    recipe: null,
    loading: true,
    isFavorite: false,
    statusBarHeight: 44
  },

  onLoad: function(options) {
    // 获取状态栏高度
    const systemInfo = wx.getSystemInfoSync();
    this.setData({
      statusBarHeight: systemInfo.statusBarHeight
    });

    const id = parseInt(options.id);
    if (!id) {
      wx.showToast({
        title: '参数错误',
        icon: 'error'
      });
      setTimeout(() => {
        wx.navigateBack();
      }, 1500);
      return;
    }

    this.setData({ id });
    this.loadRecipeDetail(id);
  },

  onShow: function() {
    // 页面显示时的处理
  },

  onShareAppMessage: function() {
    const { recipe } = this.data;
    return {
      title: recipe ? `${recipe.name} - 疗愈食谱` : '疗愈食谱',
      path: `/pages/recipe-detail/recipe-detail?id=${this.data.id}`,
      imageUrl: recipe ? recipe.image : ''
    };
  },

  onShareTimeline: function() {
    const { recipe } = this.data;
    return {
      title: recipe ? `${recipe.name} - 疗愈食谱` : '疗愈食谱',
      query: `id=${this.data.id}`,
      imageUrl: recipe ? recipe.image : ''
    };
  },

  loadRecipeDetail: function(id) {
    const recipes = this.getRecipesData();
    const recipe = recipes.find(item => item.id === id);
    
    // 模拟加载延迟
    setTimeout(() => {
      this.setData({
        recipe: recipe || recipes[0],
        loading: false
      });
    }, 400);
  },

  getRecipesData: function() {
    return [
      {
        id: 1,
        name: '晨间清心饮',
        description: '唤醒内在的生命力',
        time: '15分钟',
        level: '简单',
        calories: '120千卡',
        servings: '1人份',
        image: 'https://images.unsplash.com/photo-1623065422902-30a2d299bbe4?w=800&q=80',
        tags: ['纯素', '无麸质', '低升糖'],
        benefits: [
          {
            icon: 'auto_awesome',
            title: '细胞深层净化',
            desc: '富含高浓度叶绿素与抗氧化因子，能有效清除体内自由基，促进细胞层面的毒素代谢。'
          },
          {
            icon: 'trending_up',
            title: '代谢机能焕活',
            desc: '生姜与柠檬的独特配比可迅速点燃晨间代谢循环，通过自然的酶反应提升机体自愈力。'
          }
        ],
        ingredients: [
          { name: '羽衣甘蓝', amount: '2杯', icon: '🥬' },
          { name: '青苹果', amount: '1个', icon: '🍏' },
          { name: '柠檬', amount: '1/2个', icon: '🍋' },
          { name: '生姜', amount: '1小块', icon: '🫚' }
        ],
        steps: [
          { title: '清洗与准备', desc: '将羽衣甘蓝和青苹果用冷水彻底冲洗干净，苹果去核切块，生姜去皮备用。' },
          { title: '搅拌混合', desc: '将所有食材放入高速破壁机中，加入少量过滤水或冰块，搅打至质地完全顺滑。' },
          { title: '过滤（可选）', desc: '若追求更细腻的丝滑感，可使用细网筛或纱布滤去纤维残渣。' },
          { title: '尽情享用', desc: '倒入心仪的玻璃杯中，挤入新鲜柠檬汁，在静谧的晨间时刻细细品味。' }
        ],
        nutrition: {
          protein: '3g',
          fat: '1g',
          carbs: '28g',
          fiber: '5g'
        },
        tips: '建议早晨空腹饮用，有助于最大化吸收营养。可根据个人口味调整生姜用量。'
      },
      {
        id: 2,
        name: '清新果蔬沙拉',
        description: '低卡高纤，轻盈排毒',
        time: '15分钟',
        level: '简单',
        calories: '120卡',
        servings: '2人份',
        image: 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800&q=80',
        tags: ['低卡', '高纤维', '排毒', '素食'],
        benefits: [
          {
            icon: 'spa',
            title: '肠道健康',
            desc: '丰富的膳食纤维促进肠道蠕动，改善消化系统功能，帮助排出体内毒素。'
          },
          {
            icon: 'favorite',
            title: '心血管保护',
            desc: '多种维生素和抗氧化物质有助于降低胆固醇，保护心血管健康。'
          }
        ],
        ingredients: [
          { name: '生菜', amount: '100g', icon: '🥬' },
          { name: '小番茄', amount: '8个', icon: '🍅' },
          { name: '黄瓜', amount: '1根', icon: '🥒' },
          { name: '紫甘蓝', amount: '50g', icon: '🟣' },
          { name: '玉米粒', amount: '30g', icon: '🌽' },
          { name: '橄榄油', amount: '1勺', icon: '🫒' },
          { name: '柠檬汁', amount: '适量', icon: '🍋' }
        ],
        steps: [
          { title: '清洗蔬菜', desc: '将生菜洗净，撕成适口大小，沥干水分。' },
          { title: '切配食材', desc: '小番茄对半切开，黄瓜切片，紫甘蓝切丝。' },
          { title: '混合装盘', desc: '将所有蔬菜放入大碗中，加入玉米粒。' },
          { title: '调味享用', desc: '淋上橄榄油和柠檬汁，轻轻拌匀即可享用。' }
        ],
        nutrition: {
          protein: '3g',
          fat: '5g',
          carbs: '15g',
          fiber: '4g'
        },
        tips: '可以根据个人口味添加坚果或牛油果增加口感。建议现做现吃，保持最佳口感。'
      },
      {
        id: 3,
        name: '百合莲子羹',
        description: '安神助眠，温润滋养',
        time: '40分钟',
        level: '中等',
        calories: '180卡',
        servings: '3人份',
        image: 'https://images.unsplash.com/photo-1546548970-71785318a17b?w=800&q=80',
        tags: ['安神', '助眠', '滋补', '甜品'],
        benefits: [
          {
            icon: 'bedtime',
            title: '安神助眠',
            desc: '百合与莲子的经典搭配，具有清心安神的功效，帮助改善睡眠质量。'
          },
          {
            icon: 'opacity',
            title: '润肺养颜',
            desc: '银耳富含天然胶质，长期食用有助于润肺养颜，保持肌肤水润。'
          }
        ],
        ingredients: [
          { name: '干百合', amount: '30g', icon: '🌸' },
          { name: '莲子', amount: '50g', icon: '🪷' },
          { name: '银耳', amount: '1朵', icon: '🍄' },
          { name: '枸杞', amount: '10g', icon: '🔴' },
          { name: '冰糖', amount: '适量', icon: '🧊' },
          { name: '清水', amount: '800ml', icon: '💧' }
        ],
        steps: [
          { title: '泡发银耳', desc: '银耳提前泡发2小时，撕成小朵，去除根部硬蒂。' },
          { title: '准备配料', desc: '莲子去芯，百合洗净备用。莲子芯可保留泡茶，有清心火功效。' },
          { title: '炖煮银耳', desc: '将银耳、莲子放入锅中，加入清水，大火煮沸后转小火炖煮30分钟。' },
          { title: '加入配料', desc: '加入百合、枸杞和冰糖，继续煮10分钟至汤汁浓稠即可。' }
        ],
        nutrition: {
          protein: '5g',
          fat: '1g',
          carbs: '35g',
          fiber: '2g'
        },
        tips: '睡前1小时食用效果最佳，有助于安神助眠。糖尿病患者可减少冰糖用量。'
      },
      {
        id: 4,
        name: '红枣枸杞茶',
        description: '补气养血，日常养生',
        time: '20分钟',
        level: '简单',
        calories: '60卡',
        servings: '1人份',
        image: 'https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?w=800&q=80',
        tags: ['补气', '养血', '养生茶', '日常'],
        benefits: [
          {
            icon: 'favorite',
            title: '补气养血',
            desc: '红枣富含铁质，配合枸杞的滋补功效，是天然的补血佳品。'
          },
          {
            icon: 'visibility',
            title: '明目护眼',
            desc: '枸杞含有丰富的玉米黄质，有助于保护视力，缓解眼部疲劳。'
          }
        ],
        ingredients: [
          { name: '红枣', amount: '5颗', icon: '🔴' },
          { name: '枸杞', amount: '10g', icon: '🫘' },
          { name: '桂圆', amount: '5颗', icon: '🟤' },
          { name: '红糖', amount: '适量', icon: '🟫' },
          { name: '清水', amount: '500ml', icon: '💧' }
        ],
        steps: [
          { title: '准备材料', desc: '红枣洗净去核，桂圆剥壳备用。' },
          { title: '煮制汤底', desc: '将红枣、桂圆放入锅中，加入清水。' },
          { title: '小火慢煮', desc: '大火煮沸后转小火煮15分钟，让营养充分释放。' },
          { title: '加入调味', desc: '加入枸杞和红糖，煮5分钟后过滤享用。' }
        ],
        nutrition: {
          protein: '2g',
          fat: '0g',
          carbs: '15g',
          fiber: '1g'
        },
        tips: '经期女性和体质虚寒者特别适合饮用。可反复冲泡2-3次。'
      }
    ];
  },

  onBack: function() {
    wx.navigateBack({
      fail: () => {
        wx.switchTab({
          url: '/pages/index/index'
        });
      }
    });
  },

  onShare: function() {
    wx.showShareMenu({
      withShareTicket: true,
      menus: ['shareAppMessage', 'shareTimeline']
    });
  },

  toggleFavorite: function() {
    this.setData({
      isFavorite: !this.data.isFavorite
    });
    
    wx.showToast({
      title: this.data.isFavorite ? '已收藏' : '取消收藏',
      icon: 'none'
    });

    // 这里可以添加实际的收藏逻辑，如调用API保存到服务器
    this.saveFavoriteStatus(this.data.id, this.data.isFavorite);
  },

  saveFavoriteStatus: function(id, isFavorite) {
    // 获取本地收藏列表
    let favorites = wx.getStorageSync('favoriteRecipes') || [];
    
    if (isFavorite) {
      if (!favorites.includes(id)) {
        favorites.push(id);
      }
    } else {
      favorites = favorites.filter(item => item !== id);
    }
    
    wx.setStorageSync('favoriteRecipes', favorites);
  },

  startCooking: function() {
    const { recipe } = this.data;
    
    wx.showToast({
      title: '已加入疗愈仪式',
      icon: 'success',
      duration: 2000
    });

    // 可以添加更多逻辑，如记录到用户的每日计划
    this.addToRitual(recipe);
  },

  addToRitual: function(recipe) {
    // 获取当前日期的仪式列表
    const today = new Date().toISOString().split('T')[0];
    let rituals = wx.getStorageSync(`rituals_${today}`) || [];
    
    // 检查是否已存在
    const exists = rituals.some(r => r.id === recipe.id);
    if (!exists) {
      rituals.push({
        id: recipe.id,
        name: recipe.name,
        type: 'recipe',
        addedAt: new Date().toISOString()
      });
      wx.setStorageSync(`rituals_${today}`, rituals);
    }
  },

  // 预览图片
  previewImage: function(e) {
    const { recipe } = this.data;
    wx.previewImage({
      urls: [recipe.image],
      current: recipe.image
    });
  }
});
