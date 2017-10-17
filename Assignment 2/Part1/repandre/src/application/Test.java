package application;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] t = "deposit 50".split("\t| ");

		for(String s : t)
			System.out.println(s);
	}

}
