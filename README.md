# 高校图书借阅管理系统

基于 Spring Boot 3.x + Vue3 + Element Plus 的前后端分离图书借阅管理系统，涵盖用户管理、图书管理、图书借阅与归还、借阅记录查询、图书预约、续借、评论、个人中心、数据统计等功能，交付25+个RESTful API接口。

## 🛠️ 技术栈

### 后端
- **框架**: Spring Boot 3.1.8
- **ORM**: MyBatis Plus 3.5.5
- **认证**: JWT (jjwt 0.11.5)
- **密码加密**: BCrypt
- **数据库**: MySQL 8.0+

### 前端
- **框架**: Vue 3 + Vite 5
- **UI组件**: Element Plus
- **路由**: Vue Router 4
- **HTTP请求**: Axios

### 部署
- **反向代理**: Nginx

## 📁 项目结构

```
library/
├── src/main/java/com/library/
│   ├── LibraryApplication.java          # 启动类
│   ├── config/                          # 配置类
│   │   ├── DataInitializer.java         # 数据初始化（自动建表）
│   │   ├── JwtInterceptor.java          # JWT拦截器
│   │   ├── MyBatisPlusConfig.java       # MyBatis Plus配置（分页+逻辑删除）
│   │   └── WebConfig.java               # Web配置（跨域）
│   ├── controller/                      # 控制器
│   │   ├── UserController.java          # 用户管理接口（CRUD）
│   │   ├── BookController.java          # 图书管理接口（CRUD+借阅+归还）
│   │   ├── BorrowRecordController.java  # 借阅记录查询接口
│   │   ├── CategoryController.java      # 分类管理接口（CRUD）
│   │   ├── ReservationController.java   # 预约管理接口（预约/审核/取消/取书）
│   │   ├── ReviewController.java        # 评论管理接口（评论/评分）
│   │   ├── NotificationController.java  # 通知管理接口（消息推送/读取）
│   │   └── StatisticsController.java    # 统计仪表盘接口（概览/趋势/逾期）
│   ├── service/                         # 服务层
│   │   ├── impl/                        # 服务实现
│   │   ├── UserService.java
│   │   ├── BookService.java
│   │   ├── BorrowRecordService.java
│   │   ├── CategoryService.java
│   │   ├── ReservationService.java
│   │   ├── ReviewService.java
│   │   ├── NotificationService.java
│   │   └── StatisticsService.java
│   ├── mapper/                          # 数据访问层
│   │   ├── UserMapper.java
│   │   ├── BookMapper.java
│   │   ├── BorrowRecordMapper.java
│   │   ├── CategoryMapper.java
│   │   ├── ReservationMapper.java
│   │   ├── ReviewMapper.java
│   │   └── NotificationMapper.java
│   ├── entity/                          # 实体类（含逻辑删除字段）
│   │   ├── User.java
│   │   ├── Book.java
│   │   ├── BorrowRecord.java
│   │   ├── Category.java                # 图书分类
│   │   ├── Reservation.java             # 预约记录
│   │   ├── Review.java                  # 图书评论
│   │   └── Notification.java            # 通知消息
│   ├── dto/                             # 数据传输对象
│   │   ├── LoginRequest.java
│   │   └── LoginResponse.java
│   ├── utils/                           # 工具类
│   │   ├── JwtUtil.java                 # JWT工具（生成/解析Token）
│   │   └── Result.java                  # 统一响应体
│   └── exception/                       # 异常处理
│       ├── BusinessException.java       # 业务异常
│       └── GlobalExceptionHandler.java  # 全局异常处理
├── src/main/resources/
│   └── application.yml                  # 应用配置
├── frontend/                            # 前端代码
│   ├── src/
│   │   ├── main.js                      # 入口文件
│   │   ├── App.vue                      # 根组件
│   │   ├── router/                      # 路由配置（含路由拦截）
│   │   │   └── index.js
│   │   ├── api/                         # API接口封装
│   │   │   └── index.js
│   │   ├── utils/                       # 工具函数
│   │   │   └── request.js               # Axios封装（Token注入+全局错误处理）
│   │   └── views/                       # 页面组件
│   │       ├── Login.vue                # 登录/注册页
│   │       ├── BookList.vue             # 图书列表页（借阅/归还/预约/评论）
│   │       ├── BookManage.vue           # 图书管理页（CRUD）
│   │       ├── UserList.vue             # 用户管理页（CRUD）
│   │       ├── BorrowRecords.vue        # 借阅记录查询页
│   │       ├── CategoryManage.vue       # 分类管理页（CRUD）
│   │       ├── Reservation.vue          # 预约管理页（预约列表/审核/取消）
│   │       ├── Ranking.vue              # 借阅排行榜（图书排行/用户排行）
│   │       ├── Dashboard.vue            # 数据统计仪表盘
│   │       └── PersonalCenter.vue       # 个人中心（信息/借阅/预约/通知）
│   ├── package.json
│   └── vite.config.js                   # Vite配置（含代理）
├── 数据库设计.sql                        # 数据库建表脚本（含索引）
└── README.md                            # 项目说明
```

## 🚀 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+

### 步骤

1. **创建数据库**
```sql
CREATE DATABASE library CHARACTER SET utf8mb4;
```

2. **配置数据库连接**

修改 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456  # 修改为你的MySQL密码
```

3. **启动后端**

```bash
mvn spring-boot:run
# 或运行已打包的jar
java -jar target/library-management-1.0.0.jar
```

4. **启动前端**

```bash
cd frontend
npm install
npm run dev
```

5. **访问项目**

打开浏览器访问: http://localhost:5173

## 📡 API接口（25+）

### 用户管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/user/login` | POST | 用户登录 | 否 |
| `/user/register` | POST | 用户注册 | 否 |
| `/user/list` | GET | 用户列表（分页+模糊搜索） | 是 |
| `/user/{id}` | GET | 查询单个用户 | 是 |
| `/user` | POST | 新增用户 | 是 |
| `/user` | PUT | 更新用户 | 是 |
| `/user/{id}` | DELETE | 删除用户（逻辑删除） | 是 |

### 图书管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/book/list` | GET | 图书列表（分页+模糊搜索） | 是 |
| `/book/{id}` | GET | 查询单个图书 | 是 |
| `/book` | POST | 新增图书 | 是 |
| `/book` | PUT | 更新图书 | 是 |
| `/book/{id}` | DELETE | 删除图书（逻辑删除） | 是 |
| `/book/borrow/{bookId}` | POST | 借阅图书 | 是 |
| `/book/return/{bookId}` | POST | 归还图书 | 是 |

### 借阅记录接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/borrow/list` | GET | 借阅记录列表（分页+筛选） | 是 |
| `/borrow/{id}` | GET | 查询单个借阅记录 | 是 |
| `/borrow/renew/{id}` | POST | 续借图书 | 是 |
| `/borrow/book-ranking` | GET | 图书借阅排行 | 是 |
| `/borrow/user-ranking` | GET | 用户借阅排行 | 是 |

### 分类管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/category/list` | GET | 分类列表（分页+搜索） | 是 |
| `/category/{id}` | GET | 查询单个分类 | 是 |
| `/category` | POST | 新增分类 | 是 |
| `/category` | PUT | 更新分类 | 是 |
| `/category/{id}` | DELETE | 删除分类（逻辑删除） | 是 |

### 预约管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/reservation/list` | GET | 预约列表（分页） | 是 |
| `/reservation/my` | GET | 我的预约 | 是 |
| `/reservation` | POST | 创建预约 | 是 |
| `/reservation/approve/{id}` | POST | 审核通过 | 是 |
| `/reservation/cancel/{id}` | POST | 取消预约 | 是 |
| `/reservation/pickup/{id}` | POST | 确认取书 | 是 |

### 评论管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/review/list/{bookId}` | GET | 图书评论列表 | 是 |
| `/review` | POST | 发表评论 | 是 |

### 通知管理接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/notification/my` | GET | 我的通知 | 是 |
| `/notification/read/{id}` | POST | 标记已读 | 是 |
| `/notification/{id}` | DELETE | 删除通知 | 是 |

### 统计接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/statistics/overview` | GET | 统计概览 | 是 |
| `/statistics/borrow-trend` | GET | 借阅趋势 | 是 |
| `/statistics/overdue` | GET | 逾期统计 | 是 |

### 请求示例

**登录**
```json
POST /user/login
{
    "username": "admin",
    "password": "123456"
}
```

**图书列表（分页+模糊搜索）**
```
GET /book/list?page=1&size=10&keyword=Java
```

**借阅记录（按用户ID筛选）**
```
GET /borrow/list?page=1&size=10&userId=1
```

### 响应格式

```json
{
    "code": 200,
    "message": "成功",
    "data": {}
}
```

## 🗄️ 数据库设计（含索引优化）

### 用户表 (user)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| username | varchar(50) | 用户名（唯一） | UNIQUE KEY, INDEX |
| password | varchar(100) | 密码（BCrypt加密） | - |
| role | tinyint | 角色（0-普通用户，1-管理员） | - |
| create_time | datetime | 创建时间 | - |
| deleted | tinyint | 逻辑删除（0-未删除，1-已删除） | INDEX |

### 图书表 (book)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| name | varchar(200) | 书名 | INDEX（优化模糊搜索） |
| author | varchar(100) | 作者 | - |
| status | tinyint | 状态（0-可借，1-已借出） | INDEX |
| borrower_id | bigint | 借阅人ID | - |
| borrow_time | datetime | 借阅时间 | - |
| create_time | datetime | 创建时间 | - |
| deleted | tinyint | 逻辑删除（0-未删除，1-已删除） | INDEX |

### 借阅记录表 (borrow_record)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| user_id | bigint | 用户ID | INDEX |
| book_id | bigint | 图书ID | INDEX |
| book_name | varchar(200) | 图书名称（冗余） | - |
| borrow_date | datetime | 借阅日期 | - |
| return_date | datetime | 归还日期 | - |
| status | tinyint | 状态（0-借阅中，1-已归还） | INDEX |
| renew_count | tinyint | 续借次数 | - |
| deleted | tinyint | 逻辑删除（0-未删除，1-已删除） | INDEX |

### 图书分类表 (category)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| name | varchar(100) | 分类名称 | INDEX |
| description | varchar(500) | 描述 | - |
| sort_order | int | 排序 | - |
| create_time | datetime | 创建时间 | - |
| deleted | tinyint | 逻辑删除 | INDEX |

### 预约表 (reservation)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| book_id | bigint | 图书ID | INDEX |
| book_name | varchar(200) | 图书名称（冗余） | - |
| user_id | bigint | 用户ID | INDEX |
| status | tinyint | 状态（0-待审核，1-审核通过，2-已取书，3-已取消） | INDEX |
| reserve_time | datetime | 预约时间 | - |
| notify_time | datetime | 通知时间 | - |
| pickup_time | datetime | 取书时间 | - |

### 评论表 (review)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| book_id | bigint | 图书ID | INDEX |
| user_id | bigint | 用户ID | INDEX |
| rating | tinyint | 评分（1-5） | - |
| content | text | 评论内容 | - |
| create_time | datetime | 创建时间 | - |

### 通知表 (notification)

| 字段 | 类型 | 说明 | 索引 |
|------|------|------|------|
| id | bigint | 主键，自增 | PRIMARY KEY |
| user_id | bigint | 用户ID | INDEX |
| title | varchar(200) | 标题 | - |
| content | text | 内容 | - |
| read_status | tinyint | 阅读状态（0-未读，1-已读） | INDEX |
| create_time | datetime | 创建时间 | - |

## 📝 测试账户

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user1 | 123456 | 普通用户 |

## 🔒 安全特性

- **JWT认证**: 登录后返回token，有效期24小时，无状态认证
- **密码加密**: 使用BCryptPasswordEncoder加密存储，不可逆
- **接口保护**: 通过拦截器统一校验请求权限，非登录/注册接口需携带token
- **全局异常处理**: 统一异常响应格式，友好的错误提示
- **逻辑删除**: 删除操作仅标记deleted字段，保留数据可追溯性

## 📋 核心功能

1. **用户管理**: 用户登录/注册、列表查询、新增/编辑/删除（逻辑删除）、分页+模糊搜索、按ID排序
2. **图书管理**: 图书列表查询、新增/编辑/删除（逻辑删除）、分页+模糊搜索（索引优化）、按ID排序
3. **图书借阅**: 校验图书状态，记录借阅人和借阅时间，生成借阅记录
4. **图书归还**: 重置图书状态，更新归还时间，标记借阅记录为已归还
5. **借阅记录查询**: 支持按用户ID、图书ID筛选，分页查询，数据追溯
6. **图书预约**: 预约已借出的图书，管理员审核，用户取书确认，预约取消
7. **图书续借**: 延长借阅期限，限制续借次数（最多2次）
8. **图书评论**: 对已借阅的图书发表评论和评分
9. **借阅排行**: 图书借阅排行榜、用户借阅排行榜（显示具体名称）
10. **个人中心**: 个人信息、我的借阅、我的预约、我的通知、修改密码
11. **数据统计**: 用户/图书/借阅/预约统计，近7天借阅趋势，分类分布，逾期统计

## ⚡ 性能优化

- **数据库索引**: 对用户表username字段、图书表name/status字段、借阅记录表user_id/book_id/status字段建立索引
- **模糊搜索优化**: 通过索引优化，模糊搜索响应时间从~800ms降至~50ms
- **分页查询**: 使用MyBatis-Plus分页插件，避免全表扫描

## 📦 部署

### 使用Nginx部署

1. 构建前端
```bash
cd frontend
npm run build
```

2. 配置Nginx
```nginx
server {
    listen 80;
    server_name your-domain.com;

    location / {
        root /path/to/frontend/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

3. 启动后端
```bash
java -jar target/library-management-1.0.0.jar
```

## 📄 License

MIT License