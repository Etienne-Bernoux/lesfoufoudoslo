Remove-Item –path ./BESCOND-BERNOUX_code-java/bin/* –recurse
New-Item ./BESCOND-BERNOUX_code-java/bin -type directory


javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\UnknownAction.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\UnknownCurrency.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\ErrorCurrency.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\State.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\Currency.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\Cash.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\model\ByzantineError.java
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\spread\* -nowarn 
javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\application\Replica.java .\BESCOND-BERNOUX_code-java\src\model\FormatCommand.java .\BESCOND-BERNOUX_code-java\src\tools\IOFileParsing.java


# Shame...
#cd ./BESCOND-BERNOUX_code-java\bin\
#jar cvfe ../../AccountReplica.jar application.AccountReplica ./*
#cd ..\..\


