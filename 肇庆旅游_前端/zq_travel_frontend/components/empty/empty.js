Component({
  properties: {
    icon: {
      type: String,
      value: 'search'
    },
    title: {
      type: String,
      value: '暂无数据'
    },
    desc: {
      type: String,
      value: ''
    },
    showAction: {
      type: Boolean,
      value: false
    },
    actionText: {
      type: String,
      value: '刷新'
    }
  },
  methods: {
    onAction() {
      this.triggerEvent('action');
    }
  }
});