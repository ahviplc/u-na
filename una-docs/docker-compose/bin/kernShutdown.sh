#!/usr/bin/env bash

echo '=====开始结束运行una服务====='

echo '=====结束运行portainer可视化工具====='
docker-compose -f ../yaml/portainer.yml down

echo '=====结束运行mysql====='
docker-compose -f ../yaml/mysql.yml down

echo '=====结束运行nacos====='
docker-compose -f ../yaml/nacos.yml down

echo '=====结束运行redis====='
docker-compose -f ../yaml/redis.yml down

echo '=========================='
echo '=====结束后台服务运行====='
echo '=========================='

echo '=====结束运行una-gateway====='
docker-compose -f ../yaml/una-gateway.yml down

echo '=====结束运行una-admin====='
docker-compose -f ../yaml/una-admin.yml down

echo '=========================='
echo '=====结束前台服务运行====='
echo '=========================='

echo '=====结束运行vue-una-admin====='

echo '=====结束运行vue-una-web====='

echo '=============================='
echo '=====所有服务已经结束运行====='
echo '=============================='
