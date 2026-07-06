#!/bin/bash

APP_NAME="library-management"
APP_DIR="/opt/library"
JAR_NAME="$APP_NAME-1.0.0.jar"

echo "========================================"
echo "  图书借阅管理系统 - 部署脚本"
echo "========================================"

cd $APP_DIR

echo "[1/4] 拉取最新代码..."
git pull origin main

echo "[2/4] 构建后端..."
cd backend
mvn clean package -DskipTests

echo "[3/4] 构建前端..."
cd ../frontend
npm install
npm run build

echo "[4/4] 部署..."
cp -r dist/* /usr/share/nginx/html/library/

echo "停止旧服务..."
pkill -f $JAR_NAME

echo "启动新服务..."
nohup java -jar target/$JAR_NAME > /var/log/library.log 2>&1 &

echo "重启Nginx..."
systemctl restart nginx

echo "部署完成！"
echo "服务地址: http://localhost"