Remove-Item –path ./BESCOND-BERNOUX_code-java/bin/* –recurse
New-Item ./BESCOND-BERNOUX_code-java/bin -type directory



javac -d ./BESCOND-BERNOUX_code-java\bin\ -classpath .\BESCOND-BERNOUX_code-java\bin .\BESCOND-BERNOUX_code-java\src\spread\*


# Shame...
cd ./BESCOND-BERNOUX_code-java\bin\
jar cvfe ../../AccountReplica.jar application.AccountReplica ./*


