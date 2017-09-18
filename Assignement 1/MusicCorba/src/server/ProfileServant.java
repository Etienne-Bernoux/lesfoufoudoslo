package server;


import profileapp.ProfilerPOA;

public class ProfileServant extends ProfilerPOA {


	@Override
	public int getTimesPlayed(String song_id) {
		System.out.println("Message from client : " + song_id);
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		// TODO Auto-generated method stub
		return 3;
	}
	
}
