# 图书借阅管理系统

基于 Spring Boot 3.x + Vue3 的前后端分离图书借阅管理系统，用于实习求职简历展示。

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
- **路由**: Vue Router
- **HTTP请求**: Axios

## 📁 项目结构

```
library/
├── backend/                              # 后端代码
│   ├── src/main/java/com/library/
│   │   ├── LibraryApplication.java       # 启动类
│   │   ├── config/                       # 配置类
│   │   │   ├── DataInitializer.java      # 数据初始化
│   │   │   ├── JwtInterceptor.java       # JWT拦截器
│   │   │   ├── MyBatisPlusConfig.java    # MyBatis Plus配置
│   │   │   └── WebConfig.java            # Web配置
│   │   ├── controller/                   # 控制器
│   │   │   ├── UserController.java       # 用户接口
│   │   │   └── BookController.java       # 图书接口
│   │   ├── service/                      # 服务层
│   │   │   ├── impl/                     # 服务实现
│   │   │   ├── UserService.java
│   │   │   └── BookService.java
│   │   ├── mapper/                       # 数据访问层
│   │   │   ├── UserMapper.java
│   │   │   └── BookMapper.java
│   │   ├── entity/                       # 实体类
│   │   │   ├── User.java
│   │   │   └── Book.java
│   │   ├── dto/                          # 数据传输对象
│   │   │   ├── LoginRequest.java
│   │   │   └── LoginResponse.java
│   │   ├── utils/                        # 工具类
│   │   │   ├── JwtUtil.java              # JWT工具
│   │   │   └── Result.java               # 统一响应体
│   │   └── exception/                    # 异常处理
│   │       ├── BusinessException.java    # 业务异常
│   │       └── GlobalExceptionHandler.java # 全局异常处理
│   ├── src/main/resources/
│   │   └── application.yml               # 应用配置
│   └── pom.xml                           # Maven依赖
├── frontend/                             # 前端代码
│   ├── src/
│   │   ├── main.js                       # 入口文件
│   │   ├── App.vue                       # 根组件
│   │   ├── router/                       # 路由配置
│   │   │   └── index.js
│   │   ├── api/                          # API接口
│   │   │   └── index.js
│   │   ├── utils/                        # 工具函数
│   │   │   └── request.js                # Axios封装
│   │   └── views/                        # 页面组件
│   │       ├── Login.vue                 # 登录/注册页
│   │       └── BookList.vue              # 图书列表页
│   ├── package.json
│   └── vite.config.js                    # Vite配置
├── deploy/                               # 部署配置
│   ├── nginx.conf                        # Nginx配置
│   ├── application-prod.yml              # 生产环境配置
│   ├── deploy.sh                         # 部署脚本
│   └── startup.md                        # 启动说明
├── 数据库设计.sql                         # 数据库建表脚本
└── README.md                             # 项目说明
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

修改 `backend/src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: 123456  # 修改为你的MySQL密码
```

3. **启动后端**

```bash
cd backend
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

打开浏览器访问: http://localhost:3000

## 📡 API接口

### 用户接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/api/user/login` | POST | 用户登录 | 否 |
| `/api/user/register` | POST | 用户注册 | 否 |

### 图书接口

| 接口 | 方法 | 描述 | 认证 |
|------|------|------|------|
| `/api/book/list` | GET | 图书列表分页查询 | 是 |
| `/api/book/borrow/{bookId}` | POST | 借阅图书 | 是 |
| `/api/book/return/{bookId}` | POST | 归还图书 | 是 |

### 请求示例

**登录**
```json
POST /api/user/login
{
    "username": "admin",
    "password": "123456"
}
```

**图书列表**
```
GET /api/book/list?page=1&size=10&keyword=Java
```

### 响应格式

```json
{
    "code": 200,
    "message": "成功",
    "data": {}
}
```

## 🗄️ 数据库设计

### 用户表 (user)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键，自增 |
| username | varchar(50) | 用户名（唯一） |
| password | varchar(100) | 密码（BCrypt加密） |
| role | tinyint | 角色（0-普通用户，1-管理员） |
| create_time | datetime | 创建时间 |

### 图书表 (book)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键，自增 |
| name | varchar(200) | 书名 |
| author | varchar(100) | 作者 |
| status | tinyint | 状态（0-可借，1-已借出） |
| borrower_id | bigint | 借阅人ID |
| borrow_time | datetime | 借阅时间 |
| create_time | datetime | 创建时间 |

### 借阅记录表 (borrow_record)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | bigint | 主键，自增 |
| user_id | bigint | 用户ID |
| book_id | bigint | 图书ID |
| borrow_date | datetime | 借阅日期 |
| return_date | datetime | 归还日期 |
| status | tinyint | 状态（0-借阅中，1-已归还） |

## 📝 测试账户

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user1 | 123456 | 普通用户 |

## 🔒 安全特性

- **JWT认证**: 登录后返回token，有效期24小时
- **密码加密**: 使用BCryptPasswordEncoder加密存储
- **接口保护**: 非登录/注册接口需携带token
- **全局异常处理**: 统一异常响应格式

## 📋 核心功能

1. **用户登录/注册**: JWT认证，密码BCrypt加密
2. **图书列表**: 支持分页和按书名模糊搜索
3. **图书借阅**: 校验图书状态，记录借阅信息
4. **图书归还**: 重置图书状态和借阅信息

## 📦 部署

### 使用Nginx部署

1. 构建前端
```bash
cd frontend
npm run build
```

2. 配置Nginx (`deploy/nginx.conf`)

3. 启动后端
```bash
java -jar target/library-management-1.0.0.jar --spring.profiles.active=prod
```

## 📄 License

MIT License