#!/usr/bin/env bash

jar="target/messenger-bot-0.1-SNAPSHOT.jar"
main="com.achals.messenger.bot.MessengerBotSpringMain"

jvm_options=(
  -Xmx512m
  -Xms256m
  -XX:+UseConcMarkSweepGC
  -XX:+UseParNewGC
  -XX:ParallelGCThreads=2
  -cp ${jar}
)


find .
java "${jvm_options[@]}" "$main" "${app_options[@]}"
