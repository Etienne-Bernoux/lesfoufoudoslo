New-Item ./out -type directory
New-Item ./plot -type directory
New-Item ./plot/random -type directory
New-Item ./plot/ring -type directory
New-Item ./plot/star -type directory


# Random
Write-Output "Random Simulator"
#java -jar .\Simulator.jar ./conf/random/ccRandom30.conf > ./out/random-ccRandom30.txt
#java -jar ./Simulator.jar ./conf/random/ccRandom50.conf > ./out/random-ccRandom50.txt
#java -jar ./Simulator.jar ./conf/random/idRandom30.conf > ./out/random-idRandom30.txt
#java -jar ./Simulator.jar ./conf/random/idRandom50.conf > ./out/random-idRandom50.txt
#java -jar ./Simulator.jar ./conf/random/paRandom30.conf > ./out/random-paRandom30.txt
#java -jar ./Simulator.jar ./conf/random/paRandom50.conf > ./out/random-paRandom50.txt


# Ring
Write-Output "Ring Simulator"
#java -jar ./Simulator.jar ./conf/ring/cc30.conf > ./out/ring-cc30.txt
#java -jar ./Simulator.jar ./conf/ring/cc50.conf > ./out/ring-cc50.txt
#java -jar ./Simulator.jar ./conf/ring/id30.conf > ./out/ring-id30.txt
#java -jar ./Simulator.jar ./conf/ring/id50.conf > ./out/ring-id50.txt
#java -jar ./Simulator.jar ./conf/ring/pa30.conf > ./out/ring-pa30.txt
#java -jar ./Simulator.jar ./conf/ring/pa50.conf > ./out/ring-pa50.txt

# Star
Write-Output "Star Simulator"
#java -jar ./Simulator.jar ./conf/star/cc30.conf > ./out/star-cc30.txt
#java -jar ./Simulator.jar ./conf/star/cc50.conf > ./out/star-cc50.txt
#java -jar ./Simulator.jar ./conf/star/id30.conf > ./out/star-id30.txt
#java -jar ./Simulator.jar ./conf/star/id50.conf > ./out/star-id50.txt
#java -jar ./Simulator.jar ./conf/star/pa30.conf > ./out/star-pa30.txt
#java -jar ./Simulator.jar ./conf/star/pa50.conf > ./out/star-pa50.txt


# Parse
Write-Output "Parse output files"
java -jar Parse.jar
Write-Output "Done."