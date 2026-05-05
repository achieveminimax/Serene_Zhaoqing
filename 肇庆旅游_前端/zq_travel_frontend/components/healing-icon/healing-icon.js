Component({
  properties: {
    type: {
      type: String,
      value: 'leaf'
    },
    size: {
      type: Number,
      value: 48
    },
    color: {
      type: String,
      value: '#5D7A5D'
    },
    className: {
      type: String,
      value: ''
    }
  },

  data: {
    canvasId: '',
    iconStyle: ''
  },

  lifetimes: {
    attached() {
      const id = 'icon_' + Math.random().toString(36).substr(2, 9);
      const style = `width: ${this.properties.size}rpx; height: ${this.properties.size}rpx;`;
      this.setData({
        canvasId: id,
        iconStyle: style
      });
      this.drawIcon();
    }
  },

  observers: {
    'type, size, color': function() {
      this.drawIcon();
    }
  },

  methods: {
    drawIcon() {
      const { type, size, color } = this.properties;
      const { canvasId } = this.data;

      wx.nextTick(() => {
        try {
          const ctx = wx.createCanvasContext(canvasId, this);
          const s = size * 2;
          const c = color;

          switch(type) {
            case 'leaf':
              this.drawLeaf(ctx, s, c);
              break;
            case 'music':
              this.drawMusic(ctx, s, c);
              break;
            case 'search':
              this.drawSearch(ctx, s, c);
              break;
            case 'play':
              this.drawPlay(ctx, s, c);
              break;
            case 'pause':
              this.drawPause(ctx, s, c);
              break;
            case 'heart':
              this.drawHeart(ctx, s, c);
              break;
            case 'heart-outline':
              this.drawHeartOutline(ctx, s, c);
              break;
            case 'list':
              this.drawList(ctx, s, c);
              break;
            case 'user':
              this.drawUser(ctx, s, c);
              break;
            case 'location':
              this.drawLocation(ctx, s, c);
              break;
            case 'food':
              this.drawFood(ctx, s, c);
              break;
            case 'tea':
              this.drawTea(ctx, s, c);
              break;
            case 'mountain':
              this.drawMountain(ctx, s, c);
              break;
            case 'water':
              this.drawWater(ctx, s, c);
              break;
            case 'temple':
              this.drawTemple(ctx, s, c);
              break;
            case 'meditation':
              this.drawMeditation(ctx, s, c);
              break;
            case 'tree':
              this.drawTree(ctx, s, c);
              break;
            case 'warning':
              this.drawWarning(ctx, s, c);
              break;
            case 'time':
              this.drawTime(ctx, s, c);
              break;
            case 'target':
              this.drawTarget(ctx, s, c);
              break;
            case 'star':
              this.drawStar(ctx, s, c);
              break;
            case 'info':
              this.drawInfo(ctx, s, c);
              break;
            case 'help':
              this.drawHelp(ctx, s, c);
              break;
            case 'lock':
              this.drawLock(ctx, s, c);
              break;
            case 'flower':
              this.drawFlower(ctx, s, c);
              break;
            case 'wave':
              this.drawWave(ctx, s, c);
              break;
            default:
              this.drawLeaf(ctx, s, c);
          }

          ctx.draw();
        } catch (e) {
          console.error('Icon drawing error:', e);
        }
      });
    },

    drawLeaf(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.9);
      ctx.quadraticCurveTo(s * 0.2, s * 0.5, s * 0.5, s * 0.1);
      ctx.quadraticCurveTo(s * 0.8, s * 0.5, s * 0.5, s * 0.9);
      ctx.fill();
    },

    drawMusic(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.7, s * 0.2);
      ctx.lineTo(s * 0.7, s * 0.85);
      ctx.lineTo(s * 0.9, s * 0.75);
      ctx.lineTo(s * 0.9, s * 0.1);
      ctx.closePath();
      ctx.fill();
      ctx.beginPath();
      ctx.arc(s * 0.3, s * 0.65, s * 0.2, 0, 2 * Math.PI);
      ctx.fill();
    },

    drawSearch(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(3);
      ctx.beginPath();
      ctx.arc(s * 0.45, s * 0.45, s * 0.25, 0, 2 * Math.PI);
      ctx.stroke();
      ctx.beginPath();
      ctx.moveTo(s * 0.62, s * 0.62);
      ctx.lineTo(s * 0.9, s * 0.9);
      ctx.stroke();
    },

    drawPlay(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.3, s * 0.2);
      ctx.lineTo(s * 0.3, s * 0.8);
      ctx.lineTo(s * 0.8, s * 0.5);
      ctx.closePath();
      ctx.fill();
    },

    drawPause(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.fillRect(s * 0.25, s * 0.2, s * 0.15, s * 0.6);
      ctx.fillRect(s * 0.6, s * 0.2, s * 0.15, s * 0.6);
    },

    drawHeart(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.85);
      ctx.bezierCurveTo(s * 0.15, s * 0.55, s * 0.1, s * 0.35, s * 0.3, s * 0.2);
      ctx.bezierCurveTo(s * 0.4, s * 0.12, s * 0.5, s * 0.2, s * 0.5, s * 0.3);
      ctx.bezierCurveTo(s * 0.5, s * 0.2, s * 0.6, s * 0.12, s * 0.7, s * 0.2);
      ctx.bezierCurveTo(s * 0.9, s * 0.35, s * 0.85, s * 0.55, s * 0.5, s * 0.85);
      ctx.fill();
    },

    drawHeartOutline(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.85);
      ctx.bezierCurveTo(s * 0.15, s * 0.55, s * 0.1, s * 0.35, s * 0.3, s * 0.2);
      ctx.bezierCurveTo(s * 0.4, s * 0.12, s * 0.5, s * 0.2, s * 0.5, s * 0.3);
      ctx.bezierCurveTo(s * 0.5, s * 0.2, s * 0.6, s * 0.12, s * 0.7, s * 0.2);
      ctx.bezierCurveTo(s * 0.9, s * 0.35, s * 0.85, s * 0.55, s * 0.5, s * 0.85);
      ctx.stroke();
    },

    drawList(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.fillRect(s * 0.2, s * 0.2, s * 0.6, s * 0.1);
      ctx.fillRect(s * 0.2, s * 0.45, s * 0.6, s * 0.1);
      ctx.fillRect(s * 0.2, s * 0.7, s * 0.6, s * 0.1);
    },

    drawUser(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.3, s * 0.15, 0, 2 * Math.PI);
      ctx.fill();
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.7, s * 0.25, 0, Math.PI);
      ctx.fill();
    },

    drawLocation(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.4, s * 0.25, 0, 2 * Math.PI);
      ctx.fill();
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.65);
      ctx.lineTo(s * 0.35, s * 0.95);
      ctx.lineTo(s * 0.65, s * 0.95);
      ctx.closePath();
      ctx.fill();
    },

    drawFood(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.35, s * 0.25, Math.PI, 0);
      ctx.fill();
      ctx.fillRect(s * 0.25, s * 0.35, s * 0.5, s * 0.5);
    },

    drawTea(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);
      ctx.beginPath();
      ctx.moveTo(s * 0.25, s * 0.35);
      ctx.lineTo(s * 0.35, s * 0.85);
      ctx.lineTo(s * 0.75, s * 0.85);
      ctx.lineTo(s * 0.65, s * 0.35);
      ctx.closePath();
      ctx.stroke();
    },

    drawMountain(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.15);
      ctx.lineTo(s * 0.9, s * 0.85);
      ctx.lineTo(s * 0.1, s * 0.85);
      ctx.closePath();
      ctx.fill();
    },

    drawWater(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.1);
      ctx.quadraticCurveTo(s * 0.3, s * 0.4, s * 0.2, s * 0.5);
      ctx.quadraticCurveTo(s * 0.35, s * 0.6, s * 0.5, s * 0.7);
      ctx.quadraticCurveTo(s * 0.65, s * 0.6, s * 0.8, s * 0.5);
      ctx.quadraticCurveTo(s * 0.7, s * 0.4, s * 0.5, s * 0.1);
      ctx.fill();
    },

    drawTemple(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.1);
      ctx.lineTo(s * 0.85, s * 0.35);
      ctx.lineTo(s * 0.15, s * 0.35);
      ctx.closePath();
      ctx.fill();
      ctx.fillRect(s * 0.25, s * 0.35, s * 0.5, s * 0.15);
      ctx.fillRect(s * 0.3, s * 0.5, s * 0.15, s * 0.2);
      ctx.fillRect(s * 0.55, s * 0.5, s * 0.15, s * 0.2);
      ctx.fillRect(s * 0.25, s * 0.85, s * 0.5, s * 0.1);
    },

    drawMeditation(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(3);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.35, s * 0.12, 0, 2 * Math.PI);
      ctx.stroke();
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.47);
      ctx.lineTo(s * 0.5, s * 0.7);
      ctx.moveTo(s * 0.25, s * 0.6);
      ctx.quadraticCurveTo(s * 0.35, s * 0.75, s * 0.5, s * 0.7);
      ctx.quadraticCurveTo(s * 0.65, s * 0.75, s * 0.75, s * 0.6);
      ctx.stroke();
    },

    drawTree(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.05);
      ctx.lineTo(s * 0.75, s * 0.35);
      ctx.lineTo(s * 0.6, s * 0.35);
      ctx.lineTo(s * 0.85, s * 0.6);
      ctx.lineTo(s * 0.65, s * 0.6);
      ctx.lineTo(s * 0.8, s * 0.85);
      ctx.lineTo(s * 0.2, s * 0.85);
      ctx.lineTo(s * 0.35, s * 0.6);
      ctx.lineTo(s * 0.15, s * 0.6);
      ctx.lineTo(s * 0.4, s * 0.35);
      ctx.lineTo(s * 0.25, s * 0.35);
      ctx.closePath();
      ctx.fill();
    },

    drawWarning(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.1);
      ctx.lineTo(s * 0.9, s * 0.85);
      ctx.lineTo(s * 0.1, s * 0.85);
      ctx.closePath();
      ctx.fill();
      ctx.setFillStyle('#FFFFFF');
      ctx.fillRect(s * 0.46, s * 0.35, s * 0.08, s * 0.3);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.75, s * 0.06, 0, 2 * Math.PI);
      ctx.fill();
    },

    drawTime(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.4, 0, 2 * Math.PI);
      ctx.stroke();
      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.5);
      ctx.lineTo(s * 0.5, s * 0.25);
      ctx.moveTo(s * 0.5, s * 0.5);
      ctx.lineTo(s * 0.7, s * 0.5);
      ctx.stroke();
    },

    drawTarget(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.4, 0, 2 * Math.PI);
      ctx.stroke();
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.25, 0, 2 * Math.PI);
      ctx.stroke();
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.1, 0, 2 * Math.PI);
      ctx.stroke();
    },

    drawStar(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      for (let i = 0; i < 5; i++) {
        const angle = (i * 4 * Math.PI) / 5 - Math.PI / 2;
        const x = s * 0.5 + s * 0.4 * Math.cos(angle);
        const y = s * 0.5 + s * 0.4 * Math.sin(angle);
        if (i === 0) ctx.moveTo(x, y);
        else ctx.lineTo(x, y);
      }
      ctx.closePath();
      ctx.fill();
    },

    drawInfo(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.4, 0, 2 * Math.PI);
      ctx.fill();
      ctx.setFillStyle('#FFFFFF');
      ctx.fillRect(s * 0.46, s * 0.25, s * 0.08, s * 0.35);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.72, s * 0.05, 0, 2 * Math.PI);
      ctx.fill();
    },

    drawHelp(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.4, 0, 2 * Math.PI);
      ctx.fill();
      ctx.setFillStyle('#FFFFFF');
      ctx.fillRect(s * 0.46, s * 0.28, s * 0.08, s * 0.25);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.6, s * 0.05, 0, 2 * Math.PI);
      ctx.fill();
      ctx.fillRect(s * 0.48, s * 0.68, s * 0.04, s * 0.08);
    },

    drawLock(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.35, s * 0.2, Math.PI, 0);
      ctx.stroke();
      ctx.fillRect(s * 0.25, s * 0.35, s * 0.5, s * 0.4);
      ctx.fillRect(s * 0.42, s * 0.55, s * 0.16, s * 0.15);
    },

    drawFlower(ctx, s, c) {
      ctx.setFillStyle(c);
      for (let i = 0; i < 5; i++) {
        const angle = (i * 2 * Math.PI) / 5 - Math.PI / 2;
        const x = s * 0.5 + s * 0.25 * Math.cos(angle);
        const y = s * 0.5 + s * 0.25 * Math.sin(angle);
        ctx.beginPath();
        ctx.arc(x, y, s * 0.15, 0, 2 * Math.PI);
        ctx.fill();
      }
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.15, 0, 2 * Math.PI);
      ctx.setFillStyle('#C9A959');
      ctx.fill();
    },

    drawWave(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(3);
      ctx.setLineCap('round');
      for (let i = 0; i < 3; i++) {
        const y = s * (0.3 + i * 0.2);
        ctx.beginPath();
        ctx.moveTo(s * 0.1, y);
        ctx.quadraticCurveTo(s * 0.3, y - s * 0.1, s * 0.5, y);
        ctx.quadraticCurveTo(s * 0.7, y + s * 0.1, s * 0.9, y);
        ctx.stroke();
      }
    },

    onTap() {
      this.triggerEvent('tap');
    }
  }
});
