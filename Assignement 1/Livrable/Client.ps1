Remove-Item –path ./BESCOND-BERNOUX_code-java/bin/* –recurse
#rm -rf ./BESCOND-BERNOUX_code-java/bin/*
New-Item ./BESCOND-BERNOUX_code-java/bin -type directory


javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\profileApp\SongHelper.java .\BESCOND-BERNOUX_code-java\src\profileApp\Song.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\profileApp\UserHelper.java .\BESCOND-BERNOUX_code-java\src\profileApp\User.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\profileApp\TopTenHelper.java .\BESCOND-BERNOUX_code-java\src\profileApp\TopTen.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\*
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\profileApp\*
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\client\FormatRequest.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\exceptions\UndefinedFonctionException.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\tools\*
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\server\*
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\client\*

echo "Manifest-Version: 1.0
Class-Path: .
Main-Class: simulation.Simulateur" > ./BESCOND-BERNOUX_code-java/bin/manifest.mf
