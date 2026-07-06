# 启动命令清单

## 1. 启动 MySQL
```bash
# Ubuntu/Debian
sudo systemctl start mysql

# CentOS/RHEL
sudo systemctl start mysqld

# 验证
mysql -u root -p
```

## 2. 启动后端
```bash
# 进入后端目录
cd backend

# 编译打包
mvn clean package -DskipTests

# 启动（开发环境）
java -jar target/library-management-1.0.0.jar

# 启动（生产环境）
java -jar target/library-management-1.0.0.jar --spring.profiles.active=prod
```

## 3. 启动前端
```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 生产构建
npm run build
```

## 4. Nginx 操作
```bash
# 启动
sudo systemctl start nginx

# 重启
sudo systemctl restart nginx

# 查看状态
sudo systemctl status nginx

# 查看日志
tail -f /var/log/nginx/error.log
```

## 5. 项目结构
```
library/
├── backend/          # 后端 Spring Boot 项目
├── frontend/         # 前端 Vue3 项目
├── deploy/           # 部署配置
│   ├── nginx.conf
│   ├── application-prod.yml
│   ├── deploy.sh
│   └── startup.md
└── 数据库设计.sql     # 数据库建表脚本
```

## 6. 访问地址
- 前端页面: http://localhost:3000 (开发) / http://localhost (生产)
- 后端接口: http://localhost:8080/api

## 7. 测试账户
| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | 123456 | 管理员 |
| user1 | 123456 | 普通用户 |