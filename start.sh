#!/bin/bash

lein uberjar
java -jar target/uberjar/dispatch-0.1.0-SNAPSHOT-standalone.jar &
echo $! > dispatch.pid
