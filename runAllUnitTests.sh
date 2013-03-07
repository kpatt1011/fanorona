#!/bin/bash

echo -ne "Running the unit tests...\n"

#run all unit tests
java -classpath ./:./src/:./test/:junit-4.10.jar org.junit.runner.JUnitCore FanoronaGameBoardTest

echo -ne "\tDone!\n"
