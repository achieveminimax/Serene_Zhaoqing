Page({
  data: {
    index: null,
    isFavorite: false,
    scenicInfo: null,
    loading: true
  },

  onLoad: function(options) {
    const index = parseInt(options.index);
    if (isNaN(index)) {
      wx.showToast({
        title: '参数错误',
        icon: 'error'
      });
      setTimeout(() => {
        wx.navigateBack();
      }, 1500);
      return;
    }

    this.setData({ index });
    this.loadScenicData(index);
  },

  loadScenicData: function(index) {
    const scenicList = [
      {
        name: '鼎湖山风景区',
        category: '自然保护区',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
        aqi: 98,
        temperature: 24,
        humidity: 82,
        quote: '"云归处，心归处，此心安处是吾乡"',
        description: '隐匿于肇庆怀抱之中的鼎湖山，是一片古老寂静的 sanctuary。作为中国第一个自然保护区，它是地球鲜活的肺叶，翡翠般的湖水映照天空的辽阔，空气中弥漫着松香与宁静的气息。',
        descriptionSecondary: '沿着蜿蜒的石径每一步前行，都是邀请你放下现代世界的重负。在这里，自然的脉搏主宰着节奏，灵魂在飞瀑的旋律与竹叶的沙沙声中找回中心。',
        openTime: '06:30 - 18:00',
        difficulty: '中等难度',
        distance: '12.4 公里步道网络',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
            title: '飞水潭瀑布',
            description: '一道壮丽的垂直飞瀑，在山心处织就永恒的雾纱。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
            title: '宝莲寺',
            description: '古朴的建筑，香火的气息与山间的清风交织融合。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
            title: '云雾山径',
            description: '漫步云端的高空步道，俯瞰山谷全景的绝佳视角。'
          }
        ],
        details: [
          {
            title: '✦ 景区特色',
            items: ['原始森林覆盖率达98%以上', '负离子含量最高达每立方厘米12.5万个', '庆云寺、飞水潭等著名景点', '适合徒步、冥想、森林浴']
          },
          {
            title: '⚠ 注意事项',
            items: ['建议穿着舒适的运动鞋', '带上防晒用品和足够的饮用水', '注意保护环境，不要乱扔垃圾', '雨季请注意山路湿滑']
          },
          {
            title: '⏰ 开放时间',
            items: ['全天开放（建议游玩时间4-6小时）']
          }
        ]
      },
      {
        name: '七星岩景区',
        category: '喀斯特地貌',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
        aqi: 95,
        temperature: 26,
        humidity: 78,
        quote: '"七星坠地，碧水映天"',
        description: '七星岩，肇庆的璀璨明珠。七座石灰岩峰从星湖碧水中拔地而起，构成一幅千百年来激发无数诗人画家灵感的山水画卷。',
        descriptionSecondary: '这片喀斯特奇观将自然的壮丽与文化的底蕴完美融合。漫步于镌刻着唐代题刻的古洞，泛舟于静谧湖面，见证金色夕阳将山峰染成琥珀与玫瑰的色调。',
        openTime: '07:00 - 18:30',
        difficulty: '轻松至中等',
        distance: '20 公里环湖路线',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
            title: '天柱岩',
            description: '七座岩峰中最高的一座，可俯瞰整个景区的绝佳位置。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
            title: '千年诗廊',
            description: '跨越1200多年文学历史的古老崖壁题刻。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
            title: '五龙亭',
            description: '湖畔的如画亭台，沉思与摄影的理想之地。'
          }
        ],
        details: [
          {
            title: '✦ 景区特色',
            items: ['七座喀斯特山峰拔地而起', '长达20公里的环湖游道', '水上泛舟、垂钓、赏花', '溶洞探险、亭台楼阁']
          },
          {
            title: '◎ 推荐打卡点',
            items: ['天柱岩：登顶俯瞰全景', '千年诗廊：摩崖石刻文化', '禾花仙女：最佳观景点', '五龙亭：湖心岛休闲']
          },
          {
            title: '⚠ 注意事项',
            items: ['夏季注意防晒和补充水分', '溶洞内温度较低，建议带件外套', '乘船游览请注意安全', '周末人流量较大，建议错峰出行']
          }
        ]
      },
      {
        name: '庆云寺',
        category: '千年古刹',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
        aqi: 99,
        temperature: 23,
        humidity: 75,
        quote: '"香烟袅袅，祈愿飞扬"',
        description: '坐落于鼎湖山南麓的庆云寺，是岭南四大名刹之一。1300多年来，殿堂中回荡着僧侣的诵经声与朝圣者寻求慰藉的祈祷。',
        descriptionSecondary: '寺庙群依山而建，层层叠叠，与周围见证了数百年虔诚的古树和谐共存。这里的素食斋菜与精神氛围同样闻名，为身心提供滋养。',
        openTime: '08:00 - 17:30',
        difficulty: '轻松可达',
        distance: '鼎湖山景区内',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
            title: '大雄宝殿',
            description: '主殿供奉着一尊 magnificent 的镀金佛像。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
            title: '古白茶花',
            description: '传说中的神树，据说是禅宗六祖亲手所植。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
            title: '素斋堂',
            description: '体验将植物性饮食艺术 perfected 的寺庙素斋。'
          }
        ],
        details: [
          {
            title: '✦ 寺庙特色',
            items: ['建于唐高宗年间，历史悠久', '建筑面积达1万多平方米', '殿堂宏伟、古树参天', '斋菜素食闻名遐迩']
          },
          {
            title: '🙏 祈福项目',
            items: ['求平安：祈求身体健康、平安顺遂', '求学业：文昌阁助运学业', '求姻缘：月老殿缘分加持', '素斋体验：品尝地道斋菜']
          },
          {
            title: '⏰ 开放时间',
            items: ['开放时间：08:00-17:30', '斋菜供应：11:30-13:30']
          }
        ]
      },
      {
        name: '飞水潭',
        category: '瀑布圣境',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
        aqi: 100,
        temperature: 22,
        humidity: 88,
        quote: '"自然的交响，以流水谱成"',
        description: '飞水潭是鼎湖山最 dramatic 的一面。40米高的瀑布倾泻入清澈见底的水潭，创造了一个雾气与声音交织的自然大教堂，世代吸引着游客。',
        descriptionSecondary: '这里的空气充满负离子，营造出 profound 的 rejuvenation 氛围。据说孙中山先生曾在此游泳，被其传奇的 healing 属性所吸引。',
        openTime: '07:00 - 18:00',
        difficulty: '中等徒步',
        distance: '距山门2.5公里',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
            title: '主瀑布',
            description: '一道 thundering 的40米飞瀑，晨雾中常现彩虹。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
            title: '冥想亭',
            description: '一处宁静的所在，可沉浸于瀑布 soothing 的白噪音中。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
            title: '森林浴步道',
            description: '穿越原始森林通往瀑布的木栈道。'
          }
        ],
        details: [
          {
            title: '✦ 景点亮点',
            items: ['瀑布落差达40余米', '潭水清澈见底，凉爽宜人', '负离子含量极高，洗肺圣地', '孙中山先生曾在此游泳']
          },
          {
            title: '🧘 疗愈体验',
            items: ['瀑布冥想：听着水声放松身心', '森林浴：漫步栈道深呼吸', '观瀑品茗：在茶亭静坐品茶']
          },
          {
            title: '⚠ 注意事项',
            items: ['雨季瀑布水量更大更壮观', '注意脚下湿滑，安全第一', '请勿在瀑布下游泳', '保护水源，不要污染潭水']
          }
        ]
      },
      {
        name: '鼎湖山茶文化体验',
        category: '禅茶文化',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
        aqi: 97,
        temperature: 25,
        humidity: 80,
        quote: '"一叶一菩提，一茶一世界"',
        description: '云雾缭绕的鼎湖山坡孕育了数百年的茶叶种植。这里独特的小气候造就了 exceptional 的茶品，每一片叶子都浸润着云雾与山岚的精华。',
        descriptionSecondary: '游客被邀请参与完整的茶体验——从梯田茶园采摘嫩叶，到观摩传统制茶工艺，最终在一杯 mindful 的茶道中连接过去与现在。',
        openTime: '09:00 - 17:00',
        difficulty: '轻松体验',
        distance: '山间茶园',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
            title: '云台茶园',
            description: '云雾滋养最优质茶叶的古老茶梯田。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
            title: '传统制茶',
            description: '观看匠人通过 time-honored 技艺将鲜叶转化。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
            title: '茶道馆',
            description: '体验 meditative 的工夫茶冲泡艺术。'
          }
        ],
        details: [
          {
            title: '✦ 主要茶品',
            items: ['鼎湖山茶：清香甘醇，回甘悠长', '紫背天葵：清凉解暑，养生佳品', '野生山茶：天然无污染', '桑寄生茶：补肾强身']
          },
          {
            title: '✦ 体验项目',
            items: ['茶园观光：漫步云雾茶园', '采茶体验：亲自采摘茶叶', '制茶观摩：观看传统制茶工艺', '品茗静心：静坐品茶悟道']
          },
          {
            title: '⏰ 推荐时间',
            items: ['最佳采茶季节：春季（3-5月）', '品茶体验：全年皆宜']
          }
        ]
      },
      {
        name: '羚羊峡',
        category: '峡谷探险',
        heroImage: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/29dde14d6eadf5cc2872725b1e5109cb.jpg',
        aqi: 94,
        temperature: 27,
        humidity: 72,
        quote: '"江河刻石，岁月留痕"',
        description: '羚羊峡，华南第一峡，是奔腾的西江冲破高耸石灰岩峭壁的地方。这条7公里长的峡谷见证了千年的地质 drama 与人类历史。',
        descriptionSecondary: '曾经承载商贾与朝圣者的古栈道，如今紧贴崖壁。今天，它为 adventurous 的徒步者提供了一条穿越历史的 thrilling 路径，每个转角都展现下方咆哮江流的新视角。',
        openTime: '08:00 - 17:00',
        difficulty: '挑战路线',
        distance: '7 公里峡谷全长',
        highlights: [
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/554b863a370cb703fc96353ad1764f1b.jpg',
            title: '古栈道',
            description: '一段修复后的 historic 崖壁古道。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/678b5d3359576e39ff8bdbea91073c4a.jpg',
            title: '蜂窝岩',
            description: '风与水雕刻的独特地质奇观。'
          },
          {
            image: 'https://gitee.com/su_feng_L/picture/raw/main/轮播图/d52f9f69b71c9f46ebe76bb6a362ec99.jpg',
            title: '望江亭',
            description: '俯瞰西江峡谷的 breathtaking 观景台。'
          }
        ],
        details: [
          {
            title: '✦ 峡谷特色',
            items: ['峡谷全长7公里，江面狭窄', '两旁山峰高耸，气势磅礴', '保存完好的古栈道遗址', '历代文人墨客留下的摩崖石刻']
          },
          {
            title: '◎ 推荐路线',
            items: ['羚山栈道：全程约6公里', '沿途景点：蜂窝岩、望江亭、古栈道', '建议用时：3-4小时', '难度系数：中等']
          },
          {
            title: '⚠ 注意事项',
            items: ['部分路段较为陡峭', '注意防滑，穿着合适的鞋子', '夏季注意防晒和补水', '沿途设有休息亭和补给点']
          }
        ]
      }
    ];

    const scenicInfo = scenicList[index];
    if (scenicInfo) {
      setTimeout(() => {
        this.setData({
          scenicInfo: scenicInfo,
          loading: false
        });
      }, 400);
    } else {
      wx.showToast({
        title: '景点不存在',
        icon: 'error'
      });
      setTimeout(() => {
        wx.navigateBack();
      }, 1500);
    }
  },

  onBack: function() {
    wx.navigateBack();
  },

  onFavorite: function() {
    const newFavoriteState = !this.data.isFavorite;
    this.setData({ isFavorite: newFavoriteState });
    
    wx.showToast({
      title: newFavoriteState ? '已收藏' : '取消收藏',
      icon: 'none'
    });
  },

  onStartJourney: function() {
    wx.showModal({
      title: '开始您的旅程',
      content: '导航功能即将上线，敬请期待！',
      showCancel: false,
      confirmText: '知道了'
    });
  },

  onShareAppMessage: function() {
    const { scenicInfo } = this.data;
    return {
      title: scenicInfo ? `探索${scenicInfo.name}` : '肇庆疗愈之旅',
      path: `/pages/scenic-detail/scenic-detail?index=${this.data.index}`,
      imageUrl: scenicInfo ? scenicInfo.heroImage : ''
    };
  }
});
