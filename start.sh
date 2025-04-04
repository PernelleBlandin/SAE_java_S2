#!/bin/bash

mvn compile
mvn test
javadoc -d ./docs ./src/main/java/*.java

mvn exec:java -Dexec.mainClass="Executable"