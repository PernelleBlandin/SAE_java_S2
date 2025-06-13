#!/bin/bash

# https://ioflood.com/blog/bash-exit-on-error/#:~:text=The%20simplest%20way%20to%20make,indicating%20an%20error%20in%20bash.
set -e

mvn compile
mvn test

mvn javadoc:javadoc

# https://stackoverflow.com/questions/10108374/maven-how-to-run-a-java-file-from-command-line-passing-arguments
export JAVA_PROGRAM_ARGS=`echo "$@"`
mvn exec:java -Dexec.mainClass="Executable" -Dexec.args="$JAVA_PROGRAM_ARGS"