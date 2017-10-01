BESCOND Alexandre 		alexabes@student.matnat.uio.no
BERNOUX Etienne			etiennb@student.matnat.uio.no

Student at University of Oslo.

# Open distributed processing - Assignment n°1


## Goals 
 • To develop an object based distributed system
 • Using the CORBA programming model and middleware
 • Following the simple client/server architecture
 • Handling large data sets in a distributed environment

## Generate Jar

• Windows:
You can regenerate Jar file by execution the powershell script "GenerateJar.ps1".
• Unix:
You can regenerate Jar file by execution the shell script "GenerateJar.sh".

These two scripts generate:
 ./Server.jar
 ./Client.jar
 ./ClientTopTen.jar

## Run

• Server.jar: 
	Usage: $java -jar Server.jar True|False <song file>
	Note:
	 - The first parameter (True|False) represente the server cache.
	If True, then we enable the server cache, else we disable the server cache
     - The song file represent the path of the 3Go file with all the songs and users

• Client.jar: 
	Usage: $java -jar Client.jar True|False <input file> <output file>
	Note: The first parameter (True|False) represente the client cache.
	If True, then we enable the client cache, else we disable the client cache
	
• ClientTopTen.jar:
	Usage: $java -jar ClientTopTen.jar <output file>

## The code

The source code is located at "./BESCOND-BERNOUX_code-java".

## Prerequisites

• JavaSE 1.8 
• Permission to run script

## Help

If you have trouble to execute to code, feel free to ask your question by mail.
