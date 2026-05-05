Component({
  properties: {
    show: {
      type: Boolean,
      value: false
    },
    cover: {
      type: String,
      value: ''
    },
    songName: {
      type: String,
      value: ''
    },
    artist: {
      type: String,
      value: ''
    },
    isPlaying: {
      type: Boolean,
      value: false
    },
    currentTime: {
      type: String,
      value: '00:00'
    },
    duration: {
      type: String,
      value: '00:00'
    },
    progress: {
      type: Number,
      value: 0
    }
  },
  methods: {
    onClose() {
      this.triggerEvent('close');
    },
    onMore() {
      this.triggerEvent('more');
    },
    onPlayToggle() {
      this.triggerEvent('playToggle');
    },
    onPrev() {
      this.triggerEvent('prev');
    },
    onNext() {
      this.triggerEvent('next');
    },
    onModeChange() {
      this.triggerEvent('modeChange');
    },
    onPlaylist() {
      this.triggerEvent('playlist');
    },
    onProgressChange(e) {
      this.triggerEvent('progressChange', { value: e.detail.value });
    }
  }
});