#! /bin/bash

rm -rf ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/*

javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/constantes/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/information/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/destinations/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/sources/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/visualisations/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/transmetteurs/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/transceiver/*
javac -d ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ -classpath ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ ./BESCOND-BOU-RAMDANE-PERSON_code-java/src/simulation/*

echo "Manifest-Version: 1.0
Class-Path: .
Main-Class: simulation.Simulateur" > ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/manifest.mf

jar cfm BESCOND-BOU-RAMDANE-PERSON_simulation_lot5.jar ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/manifest.mf -C ./BESCOND-BOU-RAMDANE-PERSON_code-java/bin/ .

