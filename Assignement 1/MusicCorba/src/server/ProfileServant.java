package server;


import profileapp.ProfilerPOA;

public class ProfileServant extends ProfilerPOA {

	
	private static void simulateNetworkLatency(){
		try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getTimesPlayed(String song_id) {
		System.out.println("Message from client : " + song_id);
		
		
		ProfileServant.simulateNetworkLatency();		
		return 2;
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		System.out.println("Message from client : " + song_id);
		
		
		ProfileServant.simulateNetworkLatency();
		return 3;
	}
	
}
