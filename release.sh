#!/bin/sh
./mvnw release:clean -DskipPitest
./mvnw release:prepare -DskipPitest
./mvnw release:perform -DskipPitest

