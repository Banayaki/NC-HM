#!/usr/bin/env bash
java ./target/classes/server/TaskServer &
java ./target/classes/client/TaskClient

netstat -ltnp | grep -w "1099"

