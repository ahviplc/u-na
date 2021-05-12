#!/usr/bin/env bash

echo '=====开始安装una环境====='

echo '=====开始运行mysql====='
docker-compose -f ../yaml/mysql.yml up -d
echo '=====mysql正在进行初始化====='
./wait-for-it.sh http://localhost:3306 --timeout=60  -- echo "=====mysql已经准备就绪====="

echo '=====开始部署portainer可视化工具====='
docker-compose -f ../yaml/portainer.yml up -d

echo '=====开始运行nacos====='
docker-compose -f ../yaml/nacos.yml up -d
echo '=====nacos正在进行初始化,请等待...====='
./wait-for-it.sh http://localhost:8848 --timeout=60  -- echo "=====nacos已经准备就绪====="

echo '=====开始运行redis====='
docker-compose -f ../yaml/redis.yml up -d

echo '=====开始运行zipkin====='
docker-compose -f ../yaml/zipkin.yml up -d

echo '=====开始运行sentinel====='
docker-compose -f ../yaml/sentinel.yml up -d

echo '======================'
echo '=====开始运行后台====='
echo '======================'

echo '=====开始运行una-gateway====='
docker-compose -f ../yaml/una-gateway.yml up -d

echo '=====开始运行una-admin====='
docker-compose -f ../yaml/una-admin.yml up -d

echo '执行完成 日志目录: ./log'

echo '======================'
echo '=====开始运行前台====='
echo '======================'

echo '=====开始运行vue-una-admin 待完善====='

echo '=====开始运行vue-una-web 待完善====='

echo '======================================================'
echo '=====所有服务已经启动【请检查是否存在错误启动的】====='
echo '======================================================'
