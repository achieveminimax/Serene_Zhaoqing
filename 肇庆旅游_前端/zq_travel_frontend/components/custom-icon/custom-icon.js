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
    }
  },

  data: {
    iconId: ''
  },

  lifetimes: {
    attached() {
      this.setData({
        iconId: 'icon-' + Date.now() + '-' + Math.random()
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
      const { iconId, type, size, color } = this.data;

      wx.nextTick(() => {
        const ctx = wx.createCanvasContext(iconId, this);
        const s = size;
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
      });
    },

    drawLeaf(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);

      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.9);
      ctx.quadraticCurveTo(s * 0.2, s * 0.5, s * 0.5, s * 0.1);
      ctx.quadraticCurveTo(s * 0.8, s * 0.5, s * 0.5, s * 0.9);
      ctx.fill();

      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.3);
      ctx.lineTo(s * 0.5, s * 0.85);
      ctx.move();
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.5);
      ctx.quadraticCurveTo(s * 0.35, s * 0.45, s * 0.3, s * 0.5);
      ctx.moveTo(s * 0.5, s * 0.6);
      ctx.quadraticCurveTo(s * 0.65, s * 0.55, s * 0.7, s * 0.6);
      ctx.stroke();
    },

    drawMusic(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);

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

      ctx.beginPath();
      ctx.arc(s * 0.3, s * 0.65, s * 0.08, 0, 2 * Math.PI);
      ctx.setFillStyle('#F5F7F5');
      ctx.fill();
    },

    drawSearch(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(3);
      ctx.setLineCap('round');

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
      ctx.setFillStyle('#F5F7F5');

      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.85);
      ctx.bezierCurveTo(s * 0.15, s * 0.55, s * 0.1, s * 0.35, s * 0.3, s * 0.2);
      ctx.bezierCurveTo(s * 0.4, s * 0.12, s * 0.5, s * 0.2, s * 0.5, s * 0.3);
      ctx.bezierCurveTo(s * 0.5, s * 0.2, s * 0.6, s * 0.12, s * 0.7, s * 0.2);
      ctx.bezierCurveTo(s * 0.9, s * 0.35, s * 0.85, s * 0.55, s * 0.5, s * 0.85);
      ctx.fill();
      ctx.stroke();
    },

    drawList(ctx, s, c) {
      ctx.setFillStyle(c);

      ctx.fillRect(s * 0.2, s * 0.2, s * 0.6, s * 0.1);
      ctx.fillRect(s * 0.2, s * 0.45, s * 0.6, s * 0.1);
      ctx.fillRect(s * 0.2, s * 0.7, s * 0.6, s * 0.1);
    },

    drawUser(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setFillStyle(c);
      ctx.setLineWidth(2);

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.3, s * 0.15, 0, 2 * Math.PI);
      ctx.fill();

      ctx.beginPath();
      ctx.moveTo(s * 0.25, s * 0.85);
      ctx.quadraticCurveTo(s * 0.25, s * 0.5, s * 0.5, s * 0.5);
      ctx.quadraticCurveTo(s * 0.75, s * 0.5, s * 0.75, s * 0.85);
      ctx.stroke();
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
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);
      ctx.setFillStyle(c);

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.35, s * 0.25, Math.PI, 0);
      ctx.fill();

      ctx.beginPath();
      ctx.moveTo(s * 0.25, s * 0.35);
      ctx.lineTo(s * 0.25, s * 0.85);
      ctx.moveTo(s * 0.75, s * 0.35);
      ctx.lineTo(s * 0.75, s * 0.85);
      ctx.stroke();
    },

    drawTea(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setFillStyle(c);
      ctx.setLineWidth(2);

      ctx.beginPath();
      ctx.moveTo(s * 0.25, s * 0.35);
      ctx.lineTo(s * 0.35, s * 0.85);
      ctx.lineTo(s * 0.75, s * 0.85);
      ctx.lineTo(s * 0.65, s * 0.35);
      ctx.closePath();
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.75, s * 0.5);
      ctx.quadraticCurveTo(s * 0.9, s * 0.5, s * 0.9, s * 0.65);
      ctx.quadraticCurveTo(s * 0.9, s * 0.8, s * 0.75, s * 0.8);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.35, s * 0.55);
      ctx.lineTo(s * 0.65, s * 0.55);
      ctx.moveTo(s * 0.38, s * 0.65);
      ctx.lineTo(s * 0.62, s * 0.65);
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

      ctx.beginPath();
      ctx.moveTo(s * 0.65, s * 0.4);
      ctx.lineTo(s * 0.85, s * 0.85);
      ctx.lineTo(s * 0.45, s * 0.85);
      ctx.closePath();
      ctx.fill();

      ctx.beginPath();
      ctx.moveTo(s * 0.65, s * 0.4);
      ctx.lineTo(s * 0.75, s * 0.25);
      ctx.lineTo(s * 0.85, s * 0.4);
      ctx.closePath();
      ctx.setFillStyle('#fff');
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

      ctx.setFillStyle('rgba(255,255,255,0.5)');
      ctx.beginPath();
      ctx.ellipse(s * 0.35, s * 0.35, s * 0.08, s * 0.05, 0, 0, 2 * Math.PI);
      ctx.fill();
    },

    drawTemple(ctx, s, c) {
      ctx.setFillStyle(c);
      ctx.setStrokeStyle(c);
      ctx.setLineWidth(2);

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

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.42, s * 0.08, 0, 2 * Math.PI);
      ctx.setFillStyle('#F5F7F5');
      ctx.fill();
    },

    drawMeditation(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setFillStyle(c);
      ctx.setLineWidth(3);

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.35, s * 0.12, 0, 2 * Math.PI);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.5, s * 0.47);
      ctx.lineTo(s * 0.5, s * 0.7);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.25, s * 0.6);
      ctx.quadraticCurveTo(s * 0.35, s * 0.75, s * 0.5, s * 0.7);
      ctx.quadraticCurveTo(s * 0.65, s * 0.75, s * 0.75, s * 0.6);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(s * 0.3, s * 0.9);
      ctx.quadraticCurveTo(s * 0.5, s * 0.95, s * 0.7, s * 0.9);
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

      ctx.setFillStyle('#F5F7F5');
      ctx.fillRect(s * 0.46, s * 0.35, s * 0.08, s * 0.3);
      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.75, s * 0.06, 0, 2 * Math.PI);
      ctx.fill();
    },

    drawTime(ctx, s, c) {
      ctx.setStrokeStyle(c);
      ctx.setFillStyle(c);
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

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.05, 0, 2 * Math.PI);
      ctx.fill();
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

      ctx.beginPath();
      ctx.arc(s * 0.5, s * 0.5, s * 0.4, -Math.PI * 0.75, -Math.PI * 0.25);
      ctx.lineTo(s * 0.5, s * 0.5);
      ctx.closePath();
      ctx.setFillStyle(c);
      ctx.fill();
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

      ctx.setFillStyle('#F5F7F5');
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

      ctx.setFillStyle('#F5F7F5');
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
    }
  }
});
