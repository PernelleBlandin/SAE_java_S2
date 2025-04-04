#!/bin/bash

mvn compile
mvn test
mvn exec:java -Dexec.mainClass="Executable"