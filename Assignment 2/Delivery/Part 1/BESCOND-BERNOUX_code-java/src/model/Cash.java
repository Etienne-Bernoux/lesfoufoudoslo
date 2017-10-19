package model;


public class Cash {
	private Currency currency = null;
	private Float amount = null;
	/**
	 * By default the amount = 0.0
	 * and the devise is NOK
	 */
	public Cash() {
		super();
		this.currency = Currency.NOK;
		this.amount = new Float(0);
	}

	public Cash( Float amount, String currency) {
		switch (currency){
			case "NOK":
				this.currency= Currency.NOK;
				break;
			case "EUR":
				this.currency = Currency.EUR;
				break;
			case "USD":
				this.currency = Currency.USD;
				break;
			default:
				throw new ErrorCurrency(currency);
		}

		this.amount = amount;
	}

	public void toUSD()
	{
		if(this.currency.equals(Currency.USD))
		{
			// do nothing
		}
		else if (this.currency.equals(Currency.EUR))
		{
			this.amount = this.amount * new Float(1.0);
		}
		else if (this.currency.equals(Currency.NOK))
		{
			this.amount = this.amount * new Float(0.12);
		}
		
		this.currency = Currency.USD;
		
	}
	
	public void toEUR()
	{
		if(this.currency.equals(Currency.USD))
		{
			this.amount = this.amount * new Float(0.83);
		}
		else if (this.currency.equals(Currency.EUR))
		{
			// do nothing
		}
		else if (this.currency.equals(Currency.NOK))
		{
			this.amount = this.amount * new Float(0.10);
		}
		
		this.currency = Currency.EUR;
	}
	public void toNOK()
	{
		if(this.currency.equals(Currency.USD))
		{
			this.amount = this.amount * new Float(7.87);
		}
		else if (this.currency.equals(Currency.EUR))
		{
			this.amount = this.amount * new Float(8.55);
		}
		else if (this.currency.equals(Currency.NOK))
		{
			// do nothing
		}
		
		this.currency = Currency.NOK;
	}
	
	/**
	 * 
	 * @param percent perform an interest on the current amount
	 */
	public void addinterest(Float percent)
	{
		this.amount = this.amount * (1 + percent/100);
	}
	
	/**
	 * 
	 * @param toAdd amount to add to the current amount
	 */
	public void deposit(Float toAdd)
	{
		this.amount = this.amount + toAdd;
	}
	
	
	@Override
	public String toString()
	{
		return this.amount + " " + this.currency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Cash cash = (Cash) o;

		if (currency != cash.currency) return false;
		return amount != null ? amount.equals(cash.amount) : cash.amount == null;
	}

	@Override
	public int hashCode() {
		int result = currency != null ? currency.hashCode() : 0;
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		return result;
	}
}
