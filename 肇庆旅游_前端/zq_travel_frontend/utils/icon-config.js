module.exports = {
  iconMap: {
    '🍃': { type: 'leaf', name: 'leaf' },
    '🎵': { type: 'music', name: 'music-note' },
    '🎧': { type: 'music', name: 'headphones' },
    '🔍': { type: 'search', name: 'search' },
    '▶': { type: 'play', name: 'play' },
    '⏸': { type: 'pause', name: 'pause' },
    '❤️': { type: 'heart', name: 'like' },
    '🤍': { type: 'heart-outline', name: 'like-outline' },
    '☰': { type: 'list', name: 'list' },
    '👤': { type: 'user', name: 'people' },
    '📍': { type: 'location', name: 'location' },
    '🍽️': { type: 'food', name: 'orders' },
    '🍵': { type: 'tea', name: 'tea' },
    '🏔️': { type: 'mountain', name: 'mountain' },
    '💧': { type: 'water', name: 'water' },
    '🛕': { type: 'temple', name: 'temple' },
    '🧘': { type: 'meditation', name: 'meditation' },
    '🌲': { type: 'tree', name: 'tree' },
    '⚠️': { type: 'warning', name: 'warning' },
    '🕐': { type: 'time', name: 'time' },
    '🎯': { type: 'target', name: 'target' },
    '🌟': { type: 'star', name: 'star' },
    '✨': { type: 'star', name: 'star' },
    'ℹ️': { type: 'info', name: 'info' },
    '❓': { type: 'help', name: 'question' },
    '🔒': { type: 'lock', name: 'lock' },
    '🌸': { type: 'flower', name: 'flower' },
    '🏝️': { type: 'mountain', name: 'island' },
    '⛰️': { type: 'mountain', name: 'cliff' },
    '🚣': { type: 'water', name: 'boat' },
    '🙏': { type: 'meditation', name: 'pray' },
    '🌊': { type: 'wave', name: 'wave' },
    '🌿': { type: 'leaf', name: 'herb' },
    '👟': { type: 'user', name: 'shoes' },
    '🏪': { type: 'food', name: 'shop' }
  },

  getIconConfig(emoji) {
    return this.iconMap[emoji] || { type: 'leaf', name: 'default' };
  }
};
