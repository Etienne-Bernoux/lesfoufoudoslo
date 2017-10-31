package model;

/**
 * Created by etien on 19/10/2017.
 */
public class ErrorCurrency extends Error {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorCurrency (String currency) {
        super(currency);
    }
}
