@echo off

set path7zip=C:\Soft\sevenZip
path = %path7zip%;%path%

del stationmeteo.jar
rmdir /S /Q ch

xcopy ..\MeteoImplementation\bin . /S
xcopy ..\MeteoSpecification\bin . /S

7z a -tzip stationmeteo.jar -r ch

rmdir /S /Q ch

pause