#!/bin/bash

SUCCESS="\tSuccess!\n"
FAILURE="\tFailure!\n"


echo -ne "Compiling the project...\n"

#compile the project
if javac -d ./build/ ./src/*.java;
	then
		echo -ne $SUCCESS
	else
		echo -ne $FAILURE
		exit
fi

echo -ne "Compiling the unit tests...\n"

#compile all the tests
if javac -classpath ./:./build/:junit-4.10.jar ./test/*.java;
	then
		echo -ne $SUCCESS
	else
		echo -ne $FAILURE
		exit
fi

echo -ne "Running the unit tests...\n\n"
echo -ne "-----------------------------\n"

#run all unit tests
java -classpath ./:./build:./test/:junit-4.10.jar org.junit.runner.JUnitCore FanoronaGameBoardTest

echo -ne "-----------------------------\n"
echo -ne "\n\tDone!\n"
