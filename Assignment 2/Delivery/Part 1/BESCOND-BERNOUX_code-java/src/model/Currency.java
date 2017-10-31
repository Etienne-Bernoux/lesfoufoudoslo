package model;

public enum Currency {

	EUR, NOK, USD;
	
	public static boolean isEqual(String str, Currency cur)
	{
		if(cur.equals(Currency.EUR))
			return str.equals("EUR");
		if(cur.equals(Currency.NOK))
			return str.equals("NOK");
		if(cur.equals(Currency.USD))
			return str.equals("USD");
		return false;
	}
}
