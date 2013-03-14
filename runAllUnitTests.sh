#!/bin/bash

echo -ne "Running the unit tests...\n"

#run all unit tests
#TODO: make test names generic based on all files in the directory
# probably need regular expressions and variable
java -classpath ./:./build:./test_build/:junit-4.10.jar org.junit.runner.JUnitCore FanoronaGameBoardTest

echo -ne "\tDone!\n"
