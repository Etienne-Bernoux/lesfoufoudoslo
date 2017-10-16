package model;

import profileapp.TopTen;
import profileapp.User;

public class TopTenImpl extends TopTen{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3350860641330977538L;

	public TopTenImpl(){
		this.topTenUsers = new User[10];
	}


}
