rm -rf ./BESCOND-BERNOUX_code-java/bin/*

javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/profileapp/SongHelper.java ./BESCOND-BERNOUX_code-java/src/profileapp/Song.java
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/profileapp/UserHelper.java ./BESCOND-BERNOUX_code-java/src/profileapp/User.java
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/profileapp/TopTenHelper.java ./BESCOND-BERNOUX_code-java/src/profileapp/TopTen.java
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/model/*
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/profileapp/*
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/client/FormatRequest.java
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/tools/*
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/server/*
javac -d ./BESCOND-BERNOUX_code-java/bin/ -classpath ./BESCOND-BERNOUX_code-java/bin ./BESCOND-BERNOUX_code-java/src/client/*


# Shame...
cd ./BESCOND-BERNOUX_code-java/bin/
jar cvfe ../../Server.jar server.ProfileServer ./*
jar cvfe ../../ClientTopTen.jar client.ProfilerTopTenClient ./*
jar cvfe ../../Client.jar client.ProfilerClient ./*

