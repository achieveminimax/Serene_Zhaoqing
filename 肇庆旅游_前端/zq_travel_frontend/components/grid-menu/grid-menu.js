Component({
  properties: {
    columns: {
      type: Number,
      value: 4
    },
    items: {
      type: Array,
      value: []
    }
  },
  methods: {
    onItemTap(e) {
      const index = e.currentTarget.dataset.index;
      this.triggerEvent('itemTap', { index });
    }
  }
});