Page({
  data: {
    currentCategory: 'all',
    cartCount: 0,
    productList: [
      {
        id: 1,
        name: '山雾精油',
        price: '128.00',
        image: 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400',
        badge: '天然',
        category: 'aroma'
      },
      {
        id: 2,
        name: '森林禅意茶具',
        price: '456.00',
        image: 'https://images.unsplash.com/photo-1563822249548-9a72b6353cd1?w=400',
        category: 'tea'
      },
      {
        id: 3,
        name: '每日正念日记本',
        price: '89.00',
        image: 'https://images.unsplash.com/photo-1544816155-12df9643f363?w=400',
        category: 'books'
      },
      {
        id: 4,
        name: '檀香疗愈香',
        price: '64.00',
        image: 'https://images.unsplash.com/photo-1602928321679-560bb453f190?w=400',
        badge: '热销',
        category: 'ritual'
      },
      {
        id: 5,
        name: '薰衣草助眠喷雾',
        price: '168.00',
        image: 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400',
        category: 'aroma'
      },
      {
        id: 6,
        name: '有机白茶礼盒',
        price: '288.00',
        image: 'https://images.unsplash.com/photo-1558160074-4d7d8bdf4256?w=400',
        badge: '新品',
        category: 'tea'
      }
    ],
    allProducts: []
  },

  onLoad() {
    // 保存所有商品数据用于筛选
    this.setData({
      allProducts: this.data.productList
    });
    this.loadCartCount();
  },

  onShow() {
    this.loadCartCount();
  },

  // 加载购物车数量
  loadCartCount() {
    const cart = wx.getStorageSync('cart') || [];
    const count = cart.reduce((sum, item) => sum + (item.quantity || 1), 0);
    this.setData({ cartCount: count });
  },

  // 返回按钮点击
  onBackTap() {
    wx.navigateBack();
  },

  // 搜索按钮点击
  onSearchTap() {
    wx.navigateTo({
      url: '/pages/search/search'
    });
  },

  // 购物车按钮点击
  onCartTap() {
    wx.showToast({
      title: '购物车功能开发中',
      icon: 'none'
    });
  },

  // 分类标签点击
  onCategoryTap(e) {
    const category = e.currentTarget.dataset.category;
    this.setData({ currentCategory: category });
    
    // 筛选商品
    if (category === 'all') {
      this.setData({ productList: this.data.allProducts });
    } else {
      const filtered = this.data.allProducts.filter(item => item.category === category);
      this.setData({ productList: filtered });
    }
  },

  // 商品卡片点击
  onProductTap(e) {
    const id = e.currentTarget.dataset.id;
    wx.showToast({
      title: `查看商品 ${id}`,
      icon: 'none'
    });
  },

  // 添加到购物车
  onAddToCart(e) {
    e.stopPropagation();
    const id = e.currentTarget.dataset.id;
    const product = this.data.allProducts.find(p => p.id === id);
    
    if (!product) return;

    // 获取当前购物车
    let cart = wx.getStorageSync('cart') || [];
    
    // 检查是否已存在
    const existingIndex = cart.findIndex(item => item.id === id);
    if (existingIndex > -1) {
      cart[existingIndex].quantity = (cart[existingIndex].quantity || 1) + 1;
    } else {
      cart.push({
        ...product,
        quantity: 1
      });
    }
    
    // 保存购物车
    wx.setStorageSync('cart', cart);
    
    // 更新购物车数量
    const count = cart.reduce((sum, item) => sum + (item.quantity || 1), 0);
    this.setData({ cartCount: count });
    
    wx.showToast({
      title: '已添加到购物车',
      icon: 'success',
      duration: 1500
    });
  },

  // 特色商品点击
  onFeaturedTap() {
    wx.showToast({
      title: '查看颂钵详情',
      icon: 'none'
    });
  },

  // 下拉刷新
  onPullDownRefresh() {
    setTimeout(() => {
      wx.stopPullDownRefresh();
      wx.showToast({
        title: '刷新成功',
        icon: 'success'
      });
    }, 1000);
  },

  // 分享给朋友
  onShareAppMessage() {
    return {
      title: '疗愈商店 - 精选好物，疗愈身心',
      path: '/pages/shop-detail/shop-detail',
      imageUrl: 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=400'
    };
  }
});
