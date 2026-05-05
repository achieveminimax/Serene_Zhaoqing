Component({
  properties: {
    content: {
      type: String,
      value: ''
    }
  },

  data: {
    richText: ''
  },

  observers: {
    'content': function(newVal) {
      if (newVal) {
        this.parseMarkdown(newVal)
      }
    }
  },

  methods: {
    parseMarkdown(content) {
      const lines = content.split('\n')
      let html = ''
      let inCodeBlock = false
      let codeContent = ''
      let codeLang = ''
      let inList = false
      let i = 0

      while (i < lines.length) {
        const line = lines[i]

        // 代码块处理
        if (line.startsWith('```')) {
          if (inCodeBlock) {
            html += `<pre class="md-code-block"><div class="md-code-lang">${codeLang}</div><code class="md-code-content">${this.escapeHtml(codeContent)}</code></pre>`
            codeContent = ''
            codeLang = ''
            inCodeBlock = false
          } else {
            inCodeBlock = true
            codeLang = line.slice(3).trim()
          }
          i++
          continue
        }

        if (inCodeBlock) {
          codeContent += line + '\n'
          i++
          continue
        }

        // 关闭列表
        if (inList && !line.match(/^[-\*]\s/)) {
          html += '</ul>'
          inList = false
        }

        // 标题
        if (line.startsWith('### ')) {
          html += `<h3 class="md-h3">${this.parseInline(line.slice(4))}</h3>`
        } else if (line.startsWith('## ')) {
          html += `<h2 class="md-h2">${this.parseInline(line.slice(3))}</h2>`
        } else if (line.startsWith('# ')) {
          html += `<h1 class="md-h1">${this.parseInline(line.slice(2))}</h1>`
        }
        // 列表项
        else if (line.match(/^[-\*]\s/)) {
          if (!inList) {
            html += '<ul class="md-list">'
            inList = true
          }
          html += `<li class="md-list-item"><span class="md-list-marker">•</span><span class="md-list-content">${this.parseInline(line.slice(2))}</span></li>`
        }
        // 引用
        else if (line.startsWith('> ')) {
          html += `<blockquote class="md-blockquote">${this.parseInline(line.slice(2))}</blockquote>`
        }
        // 分割线
        else if (line.match(/^[-\*]{3,}$/)) {
          html += '<hr class="md-hr" />'
        }
        // 表格处理
        else if (line.includes('|') && i + 1 < lines.length && lines[i + 1].includes('|')) {
          const tableResult = this.parseTable(lines, i)
          html += tableResult.html
          i = tableResult.endIndex
        }
        // 空行
        else if (line.trim() === '') {
          // 忽略
        }
        // 普通段落
        else {
          html += `<p class="md-paragraph">${this.parseInline(line)}</p>`
        }
        
        i++
      }

      // 关闭未关闭的标签
      if (inCodeBlock) {
        html += `<pre class="md-code-block"><div class="md-code-lang">${codeLang}</div><code class="md-code-content">${this.escapeHtml(codeContent)}</code></pre>`
      }
      if (inList) {
        html += '</ul>'
      }

      this.setData({ richText: html })
    },

    parseInline(text) {
      let result = this.escapeHtml(text)
      
      // 粗体
      result = result.replace(/\*\*(.+?)\*\*/g, '<strong class="md-bold">$1</strong>')
      // 斜体
      result = result.replace(/\*(.+?)\*/g, '<em class="md-italic">$1</em>')
      // 行内代码
      result = result.replace(/`(.+?)`/g, '<code class="md-inline-code">$1</code>')
      // 链接
      result = result.replace(/\[(.+?)\]\((.+?)\)/g, '<a href="$2" class="md-link">$1</a>')
      
      return result
    },

    escapeHtml(text) {
      return text
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#039;')
    },

    // 解析表格
    parseTable(lines, startIndex) {
      let html = '<table class="md-table">'
      let i = startIndex
      let headerProcessed = false
      let columns = 0

      while (i < lines.length) {
        const line = lines[i].trim()

        // 空行或不含管道符的行结束表格
        if (!line || !line.includes('|')) {
          break
        }

        // 处理行：移除首尾管道符
        let rowContent = line
        if (rowContent.startsWith('|')) {
          rowContent = rowContent.slice(1)
        }
        if (rowContent.endsWith('|')) {
          rowContent = rowContent.slice(0, -1)
        }

        // 分割单元格
        const cells = rowContent.split('|').map(cell => cell.trim())

        // 检查是否是分隔线行 (第二行)
        if (!headerProcessed && cells.every(cell => /^[\s\-:|]+$/.test(cell))) {
          headerProcessed = true
          i++
          continue
        }

        // 第一行是表头
        if (!headerProcessed && i === startIndex) {
          html += '<thead><tr>'
          columns = cells.length
          cells.forEach(cell => {
            html += `<th class="md-th">${this.parseInline(cell)}</th>`
          })
          html += '</tr></thead><tbody>'
        }
        // 后续行是表格内容
        else {
          html += '<tr>'
          cells.forEach((cell, index) => {
            // 如果单元格数量少于表头，补充空单元格
            if (index < columns) {
              html += `<td class="md-td">${this.parseInline(cell)}</td>`
            }
          })
          // 补充缺少的单元格
          for (let j = cells.length; j < columns; j++) {
            html += '<td class="md-td"></td>'
          }
          html += '</tr>'
        }

        i++
      }

      html += '</tbody></table>'

      return {
        html: html,
        endIndex: i
      }
    }
  }
})
