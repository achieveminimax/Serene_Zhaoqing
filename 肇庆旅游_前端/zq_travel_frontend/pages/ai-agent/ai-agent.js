const aiConfig = require('../../config/ai-config.js')

Page({
  data: {
    // 当前选中的 Agent 类型
    currentAgent: 'qaAssistant',
    
    // 当前 Agent 信息
    currentAgentInfo: {
      name: '肇庆小助手',
      avatar: '🤖'
    },
    
    // Agent 列表
    agentList: [
      { key: 'qaAssistant', name: '肇庆小助手', avatar: '🤖', desc: '旅游问答' },
      { key: 'tripPlanner', name: '行程规划', avatar: '🗺️', desc: '定制路线' },
      { key: 'healingCompanion', name: '心灵疗愈', avatar: '🧘', desc: '情绪陪伴' }
    ],
    
    // 消息列表
    messages: [],
    
    // 输入框内容
    inputValue: '',
    
    // 是否正在加载
    loading: false,
    
    // 滚动到底部
    scrollToBottom: '',
    
    // 是否显示 Agent 选择器
    showAgentSelector: false
  },

  onLoad: function(options) {
    // 如果有传入 agent 类型，则切换
    if (options.agent && aiConfig.agents[options.agent]) {
      this.setData({ currentAgent: options.agent })
    }
    
    // 更新当前 Agent 信息
    this.updateCurrentAgentInfo()
    
    // 初始化欢迎消息
    this.initWelcomeMessage()
  },

  // 更新当前 Agent 信息
  updateCurrentAgentInfo: function() {
    const agentMap = {
      'qaAssistant': { name: '肇庆小助手', avatar: '🤖' },
      'tripPlanner': { name: '行程规划', avatar: '🗺️' },
      'healingCompanion': { name: '心灵疗愈', avatar: '🧘' }
    }
    this.setData({
      currentAgentInfo: agentMap[this.data.currentAgent]
    })
  },

  // 初始化欢迎消息
  initWelcomeMessage: function() {
    const agent = aiConfig.agents[this.data.currentAgent]
    const welcomeMsg = aiConfig.welcomeMessages[this.data.currentAgent]
    
    this.setData({
      messages: [{
        id: Date.now(),
        role: 'assistant',
        content: welcomeMsg,
        avatar: agent.avatar,
        name: agent.name,
        time: this.formatTime()
      }]
    })
  },

  // 切换 Agent
  switchAgent: function(e) {
    const agentKey = e.currentTarget.dataset.agent
    if (agentKey === this.data.currentAgent) {
      this.setData({ showAgentSelector: false })
      return
    }
    
    this.setData({
      currentAgent: agentKey,
      messages: [],
      showAgentSelector: false
    }, () => {
      this.updateCurrentAgentInfo()
      this.initWelcomeMessage()
    })
  },

  // 显示/隐藏 Agent 选择器
  toggleAgentSelector: function() {
    this.setData({
      showAgentSelector: !this.data.showAgentSelector
    })
  },

  // 输入框内容变化
  onInputChange: function(e) {
    this.setData({
      inputValue: e.detail.value
    })
  },

  // 发送消息
  sendMessage: function() {
    const content = this.data.inputValue.trim()
    if (!content || this.data.loading) return

    // 添加用户消息
    const userMessage = {
      id: Date.now(),
      role: 'user',
      content: content,
      avatar: '👤',
      name: '我',
      time: this.formatTime()
    }

    this.setData({
      messages: [...this.data.messages, userMessage],
      inputValue: '',
      loading: true
    }, () => {
      this.scrollToBottom()
      this.callAI(content)
    })
  },

  // 调用 AI API - MiniMax
  callAI: function(userContent) {
    const { apiKey, groupId, apiUrl, model, params } = aiConfig.minimax
    const agent = aiConfig.agents[this.data.currentAgent]
    
    // 构建消息历史 - OpenAI兼容格式
    const messages = []
    
    // 添加系统提示
    messages.push({
      role: 'system',
      content: agent.systemPrompt
    })
    
    // 添加历史消息（最多保留最近10条）
    const recentMessages = this.data.messages.slice(-10)
    recentMessages.forEach(msg => {
      if (msg.role !== 'system') {
        messages.push({
          role: msg.role,
          content: msg.content
        })
      }
    })
    
    // 添加当前用户消息
    messages.push({
      role: 'user',
      content: userContent
    })

    // 构建请求体 - OpenAI兼容格式
    const requestData = {
      model: model,
      messages: messages,
      temperature: params.temperature || 0.8,
      max_tokens: params.max_tokens || 2000,
      top_p: params.top_p || 0.95,
      stream: false
    }
    
    console.log('MiniMax请求:', requestData)
    console.log('请求URL:', `${apiUrl}?GroupId=${groupId}`)

    wx.request({
      url: `${apiUrl}?GroupId=${groupId}`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`
      },
      data: requestData,
      success: (res) => {
        console.log('MiniMax API响应:', res)
        
        if (res.statusCode === 200 && res.data) {
          // 检查是否有错误
          if (res.data.base_resp && res.data.base_resp.status_code !== 0) {
            console.error('MiniMax API错误:', res.data.base_resp)
            this.handleError(res.data.base_resp.status_msg || 'API返回错误')
            return
          }
          
          // MiniMax API 响应格式
          let aiResponse = ''
          
          if (res.data.reply) {
            aiResponse = res.data.reply
          } else if (res.data.choices && res.data.choices[0]) {
            aiResponse = res.data.choices[0].message?.content || res.data.choices[0].text
          }
          
          if (aiResponse && aiResponse.trim()) {
            const aiMessage = {
              id: Date.now(),
              role: 'assistant',
              content: aiResponse,
              avatar: '🌿',
              name: '疗愈助手',
              time: this.formatTime()
            }

            this.setData({
              messages: [...this.data.messages, aiMessage],
              loading: false
            }, () => {
              this.scrollToBottom()
            })
          } else {
            console.error('AI返回空回复:', res.data)
            this.handleError('AI返回空回复，请重试')
          }
        } else {
          console.error('API错误:', res.statusCode, res.data)
          this.handleError(`API错误: ${res.statusCode}`)
        }
      },
      fail: (err) => {
        console.error('API 调用失败:', err)
        this.handleError('网络请求失败，请检查网络连接')
      },
      complete: () => {
        // 确保loading状态被重置
        this.setData({ loading: false })
      }
    })
  },

  // 处理错误
  handleError: function(errorMsg) {
    const errorMessage = {
      id: Date.now(),
      role: 'system',
      content: `抱歉，${errorMsg}。请稍后重试。`,
      avatar: '⚠️',
      name: '系统',
      time: this.formatTime(),
      isError: true
    }

    this.setData({
      messages: [...this.data.messages, errorMessage],
      loading: false
    }, () => {
      this.scrollToBottom()
    })
  },

  // 滚动到底部
  scrollToBottom: function() {
    this.setData({
      scrollToBottom: `msg-${this.data.messages.length - 1}`
    })
  },

  // 格式化时间
  formatTime: function() {
    const now = new Date()
    const hours = now.getHours().toString().padStart(2, '0')
    const minutes = now.getMinutes().toString().padStart(2, '0')
    return `${hours}:${minutes}`
  },

  // 快速提问
  quickAsk: function(e) {
    const question = e.currentTarget.dataset.question
    this.setData({ inputValue: question }, () => {
      this.sendMessage()
    })
  },

  // 返回上一页
  goBack: function() {
    wx.navigateBack()
  },

  // 清空对话
  clearChat: function() {
    wx.showModal({
      title: '确认清空',
      content: '确定要清空当前对话吗？',
      success: (res) => {
        if (res.confirm) {
          this.setData({ messages: [] }, () => {
            this.initWelcomeMessage()
          })
        }
      }
    })
  },

  // 复制消息
  copyMessage: function(e) {
    const content = e.currentTarget.dataset.content
    wx.setClipboardData({
      data: content,
      success: () => {
        wx.showToast({
          title: '已复制',
          icon: 'success',
          duration: 1500
        })
      }
    })
  },

  // 点赞消息
  likeMessage: function(e) {
    const id = e.currentTarget.dataset.id
    wx.showToast({
      title: '已点赞',
      icon: 'success',
      duration: 1500
    })
  }
})
