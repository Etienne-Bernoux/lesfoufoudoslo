package application;

import model.FormatCommand;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Queue<FormatCommand> queue = new ConcurrentLinkedQueue<>();

       queue.add(new FormatCommand(new String[]{"toto1", "toto2", "toto3"}));
       queue.add(new FormatCommand(new String[]{"toto4", "toto5", "toto6"}));
       queue.add(new FormatCommand(new String[]{"toto17", "toto8", "toto9"}));


		while (!queue.isEmpty()) {
			System.out.println(queue.poll());
		}
	}

}
