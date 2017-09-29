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

echo "Main-Class: server.ProfileServer
" > ./BESCOND-BERNOUX_code-java/bin/Manifest.txt

jar cfm server.jar ./BESCOND-BERNOUX_code-java/bin/Manifest.txt ./BESCOND-BERNOUX_code-java/bin/server/*.class
