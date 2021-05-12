#!/usr/bin/env bash

echo '=====开始结束运行una项目====='

echo '=====结束运行una-admin====='
docker-compose -p una-admin -f ../yaml/una-admin.yml down

echo '=====结束运行una-provider====='
docker-compose -p una-provider -f ../yaml/una-provider.yml down

echo '=====结束运行una-consumer====='
docker-compose -p una-consumer -f ../yaml/una-consumer.yml down

echo '=====结束运行una-gateway====='
docker-compose -p una-gateway -f ../yaml/una-gateway.yml down

echo '=============================='
echo '=====所有una项目服务已经结束运行====='
echo '=============================='
