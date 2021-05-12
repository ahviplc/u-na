#!/usr/bin/env bash

echo '=====开始启动una项目====='

echo '=====开始运行una-gateway====='
docker-compose -p una-gateway -f ../yaml/una-gateway.yml up -d

echo '=====开始运行una-admin====='
docker-compose -p una-admin -f ../yaml/una-admin.yml up -d

echo '=====开始运行una-provider====='
docker-compose -p una-provider -f ../yaml/una-provider.yml up -d

echo '=====开始运行una-consumer====='
docker-compose -p una-consumer -f ../yaml/una-consumer.yml up -d

echo '执行完成 日志目录: ./log'

echo '================================================================='
echo '=====【微服务启动需要耗费一定时间，请到Nacos中查看启动情况】====='
echo '================================================================='
