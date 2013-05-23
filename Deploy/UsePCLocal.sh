#!/bin/sh

# Détection 32/64 bits
if [ `uname -m | grep 64 | wc -l` ]; then
	bits=64
else
	bits=32
fi

java -cp .:./*:ext/* -Djava.library.path=ext/lib$bits ch.hearc.meteo.imp.use.remote.UsePCLocal
