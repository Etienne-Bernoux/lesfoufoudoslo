package application;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.FormatCommand;
import model.UnknownAction;
import spread.SpreadException;
import tools.IOFileParsing;

import static java.lang.System.exit;

public class AccountReplica {
	
	
	private static void printHelpStatement()
	{
		System.out.println("balance");
		System.out.println("	This command causes the client to print the current balance on the account.");
		System.out.println("");
		System.out.println("deposit <amount>");
		System.out.println("	This command causes the balance to increase by <amount>. This increase should be");
		System.out.println("	performed on all the replicas in the group.");
		System.out.println("");
		System.out.println("addinterest <percent>");
		System.out.println("	This command causes the balance to increase by <percent> percent of the current value. In");
		System.out.println("	other words, the balance should be multiplied by (1 + <percent>/100). This update should");
		System.out.println("	be performed on all the replicas in the group.");
		System.out.println("");
		System.out.println("exchange <from> <to>");
		System.out.println("	Exchanges the currency from e.g. NOK to USD. Support these currencies only: NOK,");
		System.out.println("	USD and EUR. Look at the �Currency table� down below for exchange rates.");
		System.out.println("");
		System.out.println("memberinfo");
		System.out.println("	Returns the names of the current participants in the group, and prints it to the screen.");
		System.out.println("");
		System.out.println("sleep <duration>");
		System.out.println("	This command causes the client to do nothing for <duration> seconds. It is only useful in");
		System.out.println("	a batch file.");
		System.out.println("");
		System.out.println("exit");
		System.out.println("	This command causes the client to exit. Alternatively, the client process can be just killed");
		System.out.println("	by the means of the operating system.");
		System.out.println("quit");
		System.out.println("	This command quit the application and exit every local client.");
	}

	public static void main(String[] args) throws IOException {

		if(args.length != 3 && args.length != 4)
		{
			System.out.println("Usage: accountReplica <server address> <account name> <number of replicas> [file name]");
			return;
		}

		String serverAddr = args[0];
		String accountName = args[1];
		Integer nbReplicas = new Integer(args[2]);
		String fileName = args.length == 4 ? args[3] : null;
		Boolean allRelplicaReady = false;
		
		
		// Creation of the replica and launch each listener
		List<Replica> replicas = new ArrayList<Replica>(nbReplicas);
		
		for(int i = 0; i < nbReplicas; i++)
		{
			try {
				replicas.add(new Replica(accountName, serverAddr));
			} catch (SpreadException e) {
				System.out.printf("The %d-th replica has not been created, check the daemon running and config\n",i);
				exit(1);
			}
		}

		// Wait for all replica being ready

		while (!allRelplicaReady){
			allRelplicaReady = true;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(Replica replica: replicas)
			{
				allRelplicaReady = allRelplicaReady && replica.isReady();
			}
		}


		/**
		 *
		 * Put code init client
		 */

		// Then we print a prompt
		if(fileName == null)
		{
			int i = 1;
			boolean quit = false;
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Pattern emptyLine = Pattern.compile("^[ \t]*$");
			Matcher m = null;
			FormatCommand fc = null;

			System.out.println("Welcome on our Non-UI bank account. Write \"exit\" to show the command list:");

			while(!quit)
			{
				Replica repToUse = null;
				while(repToUse == null)
				{
					repToUse = replicas.get(i%nbReplicas);
					if(repToUse.isDisconnected())
					{
						replicas.remove(i%nbReplicas);
						nbReplicas = nbReplicas - 1;
						if(nbReplicas.equals(new Integer(0)))
						{
							quit = true;
						}
						repToUse = null;
					}
				}
				if(nbReplicas.equals(new Integer(0)))
				{
					quit = true;
				}
				else
				{
					System.out.print("["+ i + "]: ");
					System.out.flush();
						line = br.readLine();
						m = emptyLine.matcher(line);
						// If we do not have an empty line
						if(!m.find())
						{
							fc = IOFileParsing.getFormatCommandFromLine(line);
							switch (fc.getAction())
							{
								case "help":
									AccountReplica.printHelpStatement();
									break;
								case "quit":
									quit = true;
									break;
								default:
									try {
										fc.sendWith(replicas.get(i%nbReplicas));
									} catch (UnknownAction e) {
										System.out.println("Unkown action! See help.");
									}
							}

							i = i + 1;
						}




				}

			}
			br.close();

			System.out.println("Ciao!");

		}
		// Then we execute the file
		else
		{
			BufferedReader br = null;
			// open input file
			try {
				br = new BufferedReader(new FileReader(fileName));
				String line = null;
				FormatCommand fc = null;
				int i = 0;
				while ((line = br.readLine()) != null)
				{
					fc = IOFileParsing.getFormatCommandFromLine(line);
					try {
						fc.sendWith(replicas.get(i%nbReplicas));
					} catch (UnknownAction e) {
						System.out.println("Unknown action! See help.");
					}
					i ++;
				}
			} catch (IOException e) {
				System.out.println("File reading error");
			}finally {
				try {
					if(br != null){
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// we stop each thread
		for(int i = 0; i < nbReplicas; i++)
		{
			//TODO
		}

	}

}
