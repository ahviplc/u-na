#!/usr/bin/env bash

echo '=====开始更新una镜像====='

echo '=====开始关闭运行的容器====='
sh kernShutdown.sh

echo '=====开始更新una-gateway====='
docker pull ahviplc/una-gateway:1.0.0

echo '=====开始更新una-admin====='
docker pull ahviplc/una-admin:1.0.0

echo '=====删除docker标签为none的镜像====='
docker images | grep none | awk '{print $3}' | xargs docker rmi

echo '=====开始运行的一键部署脚本====='
sh kernStartup.sh
