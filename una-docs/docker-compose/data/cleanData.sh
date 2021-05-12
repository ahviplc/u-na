#!/usr/bin/env bash

echo '=====开始清空una中的数据====='

echo '=====开始清空una_data数据====='
#rm -rf ./una_data/*

echo '=====开始清空mysql数据====='
rm -rf ./mysql_data/*

echo '=====开始清空portainer数据====='
rm -rf ./portainer/data/*

echo '=====开始清空redis数据====='
rm -rf ./redis_data/*

echo '=====开始清空log日志====='
rm -rf ../log/*

echo '=============================='
echo '=====所有数据已经被清空======='
echo '=============================='
