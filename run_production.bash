#!/usr/bin/env bash

jar="target/messenger-bot-0.1-SNAPSHOT.jar"
main="com.twitter.common.application.AppLauncher"

jvm_options=(
  -Xmx384m
  -Xms256m
  -XX:+UseConcMarkSweepGC
  -XX:+UseParNewGC
  -XX:ParallelGCThreads=2
  -cp $jar
)

app_options=(
  -app_class=com.achals.messenger.bot.MessengerBotMain
  -http_port=$PORT
  -use_glog=true
  -use_glog_formatter=true
  -guice_stage=PRODUCTION
)

java "${jvm_options[@]}" "$main" "${app_options[@]}"
