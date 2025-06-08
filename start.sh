#!/bin/bash

# https://ioflood.com/blog/bash-exit-on-error/#:~:text=The%20simplest%20way%20to%20make,indicating%20an%20error%20in%20bash.
set -e

mvn compile
mvn test

mvn javadoc:javadoc

mvn exec:java -Dexec.mainClass="Executable"