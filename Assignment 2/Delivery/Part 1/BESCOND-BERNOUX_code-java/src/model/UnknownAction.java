package model;

@SuppressWarnings("serial")
public class UnknownAction extends Exception {

	public UnknownAction(String action) {
		super(action);
	}

}
