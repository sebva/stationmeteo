set bits=32
if %PROCESSOR_ARCHITECTURE%==AMD64 set bits=64

java -cp .;./*;ext/* -Djava.library.path=ext/lib%bits% ch.hearc.meteo.imp.use.remote.UsePCLocal