package exceptions;

public class UndefinedFonctionException extends Exception {


	private static final long serialVersionUID = 5374877151812396806L;

    public UndefinedFonctionException() {
    	super();
    }
   
    public UndefinedFonctionException(String function) {
    	super(function);
    }
}
