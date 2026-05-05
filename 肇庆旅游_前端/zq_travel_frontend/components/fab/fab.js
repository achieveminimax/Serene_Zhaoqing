Component({
  properties: {
    show: {
      type: Boolean,
      value: false
    },
    expanded: {
      type: Boolean,
      value: false
    },
    actions: {
      type: Array,
      value: []
    }
  },
  methods: {
    onMainTap() {
      this.triggerEvent('mainTap');
    },
    onActionTap(e) {
      const index = e.currentTarget.dataset.index;
      this.triggerEvent('actionTap', { index });
    }
  }
});