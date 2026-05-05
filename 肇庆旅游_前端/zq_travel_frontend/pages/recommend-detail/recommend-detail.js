Page({
  data: {
    id: null,
    title: '',
    description: '',
    decoration: '',
    image: '',
    scenicInfo: null,
    loading: true
  },

  onLoad: function(options) {
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
    this.loadDetailData(id);
  },

  loadDetailData: function(id) {
    // 模拟加载数据
    const scenicData = this.getScenicData(id);
    
    setTimeout(() => {
      this.setData({
        ...scenicData,
        loading: false
      });
    }, 300);
  },

  getScenicData: function(id) {
    const scenicList = [
      {
        id: 1,
        title: '鼎湖山森林氧吧一日游',
        description: '岭南四大名山 · 天然氧吧 · 负离子含量极高',
        decoration: '🌲',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
        scenicInfo: {
          emoji: '🏔️',
          name: '鼎湖山风景区',
          subtitle: '岭南四大名山 · 天然氧吧',
          desc: '鼎湖山位于广东省肇庆市，是岭南四大名山之一。这里空气清新，负离子含量极高，被誉为"天然氧吧"，是疗愈身心的绝佳去处。',
          sections: [
            {
              title: '✦ 景区特色',
              items: ['• 原始森林覆盖率达98%以上', '• 负离子含量最高达每立方厘米12.5万个', '• 庆云寺、飞水潭等著名景点', '• 适合徒步、冥想、森林浴']
            },
            {
              title: '⚠ 注意事项',
              items: ['• 建议穿着舒适的运动鞋', '• 带上防晒用品和足够的饮用水', '• 注意保护环境，不要乱扔垃圾', '• 雨季请注意山路湿滑', '• 部分区域需注意蚊虫防护']
            },
            {
              title: '⏰ 开放时间',
              items: ['全天开放（建议游玩时间4-6小时）']
            }
          ],
          tags: ['📍 广东肇庆', '🌿 森林氧吧', '🧘 疗愈圣地']
        }
      },
      {
        id: 2,
        title: '七星岩喀斯特奇观探秘',
        description: '峰奇石异洞幽 · 岭南第一奇观 · 湖光山色',
        decoration: '✨',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
        scenicInfo: {
          emoji: '🏝️',
          name: '七星岩景区',
          subtitle: '岭南第一奇观 · 喀斯特地貌',
          desc: '七星岩位于肇庆市区北郊，由七座石灰岩峰组成，以"峰奇、石异、洞幽、水秀"著称。这里湖光山色交相辉映，被誉为"岭南第一奇观"，是休闲度假的绝佳去处。',
          sections: [
            {
              title: '✦ 景区特色',
              items: ['• 七座喀斯特山峰拔地而起', '• 长达20公里的环湖游道', '• 水上泛舟、垂钓、赏花', '• 溶洞探险、亭台楼阁', '• 夕阳西下时湖面金光闪闪']
            },
            {
              title: '◎ 推荐打卡点',
              items: ['• 天柱岩：登顶俯瞰全景', '• 千年诗廊：摩崖石刻文化', '• 禾花仙女：最佳观景点', '• 五龙亭：湖心岛休闲', '• 牌坊公园：音乐喷泉表演']
            },
            {
              title: '⚠ 注意事项',
              items: ['• 夏季注意防晒和补充水分', '• 溶洞内温度较低，建议带件外套', '• 乘船游览请注意安全', '• 周末人流量较大，建议错峰出行', '• 禁止在湖中游泳']
            },
            {
              title: '⏰ 开放时间',
              items: ['全天开放（建议游玩时间3-5小时）', '牌坊音乐喷泉：每晚20:00-20:30']
            }
          ],
          tags: ['📍 广东肇庆', '🏝️ 喀斯特地貌', '🚣 水上娱乐']
        }
      },
      {
        id: 3,
        title: '肇庆疗愈美食地图',
        description: '地道风味 · 养生佳品 · 特色小吃',
        decoration: '🌸',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/recipe/0c8a7c8723bf9808521d72e028cc5eba.jpg',
        scenicInfo: {
          emoji: '🍜',
          name: '肇庆疗愈美食',
          subtitle: '地道风味 · 养生佳品',
          desc: '肇庆不仅有秀美的山水，更有丰富的美食文化。这里的食材天然新鲜，烹饪方法讲究养生，是美食爱好者的天堂。',
          sections: [
            {
              title: '✦ 必尝美食',
              items: ['• 鼎湖上素：庆云寺招牌斋菜', '• 裹蒸粽：肇庆传统名点', '• 文庆鲤：西江特产', '• 麦溪鲩：肉质鲜美', '• 紫背天葵：清凉养生茶']
            },
            {
              title: '🍵 养生茶饮',
              items: ['• 鼎湖山茶：清香甘醇', '• 桑寄生茶：补肾强身', '• 溪黄草茶：清热祛湿', '• 罗汉果茶：润肺止咳']
            },
            {
              title: '📍 推荐去处',
              items: ['• 牌坊广场美食街', '• 鼎湖山斋菜馆', '• 西江渔村', '• 古城老街小吃']
            }
          ],
          tags: ['📍 肇庆各地', '🍜 地道美食', '🍵 养生茶饮']
        }
      },
      {
        id: 4,
        title: '庆云寺禅修静心之旅',
        description: '千年古刹 · 禅茶一味 · 修身养性',
        decoration: '🙏',
        image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
        scenicInfo: {
          emoji: '🛕',
          name: '庆云寺',
          subtitle: '岭南第一名刹 · 千年古刹',
          desc: '庆云寺位于鼎湖山南麓，是岭南四大名刹之一，距今已有1300多年历史。寺庙依山而建，环境清幽，香火鼎盛，是修身养性、祈福求安的圣地。',
          sections: [
            {
              title: '✦ 寺庙特色',
              items: ['• 建于唐高宗年间，历史悠久', '• 建筑面积达1万多平方米', '• 殿堂宏伟、古树参天', '• 斋菜素食闻名遐迩', '• 寺中白茶花相传为禅宗六祖手植']
            },
            {
              title: '🙏 祈福项目',
              items: ['• 求平安：祈求身体健康、平安顺遂', '• 求学业：文昌阁助运学业', '• 求姻缘：月老殿缘分加持', '• 素斋体验：品尝地道斋菜', '• 抄经禅修：静心养性']
            },
            {
              title: '⏰ 开放时间',
              items: ['开放时间：08:00-17:30', '斋菜供应：11:30-13:30']
            }
          ],
          tags: ['📍 鼎湖山景区内', '🛕 千年古刹', '🙏 祈福圣地']
        }
      }
    ];

    const data = scenicList.find(item => item.id === id);
    return data || scenicList[0];
  },

  onBack: function() {
    wx.navigateBack();
  },

  onShare: function() {
    wx.showShareMenu({
      withShareTicket: true,
      menus: ['shareAppMessage', 'shareTimeline']
    });
  },

  onFavorite: function() {
    wx.showToast({
      title: '已收藏',
      icon: 'success'
    });
  },

  onNavigate: function() {
    wx.showToast({
      title: '导航功能开发中',
      icon: 'none'
    });
  }
});
