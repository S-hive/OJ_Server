# HiveOJ Frontend
基于 Spring Boot + MQ + Docker 的编程题目评测系统。系统能够根据管理员预设的题目用例对用户提交的代码进行执行和评测；系统中 [代码沙箱](https://github.com/S-hive/CodeSandbox) 可作为独立服务供其他开发者调用。

## 功能截图
<img width="1844" height="1050" alt="image" src="https://github.com/user-attachments/assets/50980a4f-b6eb-4ffb-8d79-0b17232c22ec" />
<img width="1844" height="1050" alt="image" src="https://github.com/user-attachments/assets/45ff2fbc-7707-494b-be87-0f6af265cc8d" />
<img width="1844" height="1050" alt="image" src="https://github.com/user-attachments/assets/83732cd3-1ed3-4d7e-96c1-df9433a999e6" />
<img width="1863" height="1045" alt="image" src="https://github.com/user-attachments/assets/1bcd83b9-e64f-42b9-96d1-91bc4cae397d" />



## 功能特性
- **题目浏览**：按标题、标签搜索题目，支持分页与通过率展示
- **在线做题**：Markdown 题目渲染 + Monaco 代码编辑器，支持 Java / C++ / Go
- **代码提交**：提交代码至后端判题，查看判题状态与结果
- **提交记录**：按题号、编程语言筛选历史提交
- **用户系统**：用户注册、登录，基于 Session 的自动登录
- **权限控制**：路由级权限（游客 / 普通用户 / 管理员）
- **题目管理**（管理员）：
  - 创建 / 编辑题目（Markdown 编辑器）
  - 配置判题参数（时间 / 内存 / 堆栈限制）
  - 配置判题用例（输入 / 输出）
  - 题目列表管理与删除

## 技术选型

本项目采用前后端分离架构，基于 Spring Boot 与 Vue3 生态构建。

### 前端

- **核心框架**：Vue 3 + TypeScript + Vuex
- **UI 组件库**：Arco Design
- **自动化工程**：OpenAPI 接口代码自动生成

### 后端

- **数据存储**：MySQL + Redis (分布式 Session)
- **消息队列**：RabbitMQ (异步解耦评测任务)
- **核心亮点**：
  - **Docker 代码沙箱**：基于容器技术的独立代码执行环境
  - **设计模式实战**：策略模式、工厂模式等深度应用

## 核心流程时序图
<img width="1332" height="974" alt="image" src="https://github.com/user-attachments/assets/760a5dd7-04f3-4a1a-819f-44baa1454a27" />

## 快速开始

### 环境要求

- Node.js >= 14
- npm >= 6
- [前端](https://github.com/S-hive/OJ_Frontend)

### 安装依赖

```bash
npm install
```
### 启动开发服务器
```bash
npm run serve
```







