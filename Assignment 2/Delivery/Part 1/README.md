BESCOND Alexandre 		alexabes@student.matnat.uio.no
BERNOUX Etienne			etiennb@student.matnat.uio.no

Student at University of Oslo.

# Open distributed processing - Assignment n°2


## Goals 

 • Develop a distributed application that models a replicated bank account.
 • The implementation should follow the “replicated state machine” paradigm on top of group communication.
 • Using the Spread Toolkit

## Generate Jar

• Windows:
You can regenerate Jar file by execution the powershell script "GenerateJar.ps1".

This scripts generate:
 ./repandre.jar

## Run

• repandre.jar: 
	Usage: $java -jar repandre <server address> <account name> <number of replicas> [file name]
	Note:
	<server address>
		is the address of the Spread server that the client should connect to.
	<account name> 
		stands for the name of the account.
	<number of replicas>
		is the number of clients that will be initially deployed for <account name>.
	[file name]
		is the file that client wll perform batch processing of commands and exit.
		For the liste of command see Manual part.

## Manual

• balance
	This command causes the client to print the current balance on the account.
• deposit <amount>
	This command causes the balance to increase by <amount>. This increase should be performed on all the replicas in the group.
• addinterest <percent>
	This command causes the balance to increase by <percent> percent of the current value. In other words, the balance should be multiplied by (1 + <percent>/100). This update should be performed on all the replicas in the group.
• exchange <from> <to>
	Exchanges the currency from e.g. NOK to USD. Support these currencies only: NOK, USD and EUR. Look at the “Currency table” down below for exchange rates.
• memberinfo
	Returns the names of the current participants in the group, and prints it to the screen.
• sleep <duration>
	This command causes the client to do nothing for <duration> seconds. It is only useful in a batch file.
• exit
	This command causes the client to exit. Alternatively, the client process can be just killed by the means of the operating system.


## The code

The source code is located at "./BESCOND-BERNOUX_code-java".

## Prerequisites

• JavaSE 1.8 
• Permission to run script
• Spread Deamon

## Help

If you have trouble to execute to code, feel free to ask your question by mail.
