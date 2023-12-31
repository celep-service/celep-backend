#!/bin/bash
BUILD_JAR=$(ls /home/ubuntu/celep-backend/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/deploy/deploy.log

echo "> build 파일 복사" >> /home/ubuntu/deploy/deploy.log
DEPLOY_PATH=/home/ubuntu/deploy/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/deploy/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/deploy/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "$(date '+%Y-%m-%d %H:%M:%S') > DEPLOY_JAR 배포" >> /home/ubuntu/deploy/deploy.log
nohup java -Dfile.encoding=UTF-8 -jar \
  $DEPLOY_JAR >> /dev/null 2>/home/ubuntu/deploy/deploy_err.log &
