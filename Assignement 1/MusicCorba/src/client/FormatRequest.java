package client;

public class FormatRequest {
	
	
	public FormatRequest(String[] request)
	{
		this.nameFonction = request[0];
		
		if(nameFonction.equals("getTimesPlayed"))
		{
			this.songId = request[1];
		}
		else if(nameFonction.equals("getTimesPlayedByUser")){
			this.userId = request[1];
			this.songId = request[2];
		}
		
	}
	
	public String getNameFonction() {
		return this.nameFonction;
	}

	public String getSongId() {
		return this.songId;
	}

	public String getUserId() {
		return this.userId;
	}


	
	private String nameFonction = null;
	private String songId = null;
	private String userId = null;
	
	

}
