package server;

public class FormatData {
	
	private final String userId;
	private final String songId;
	private final Integer nbPlay;
	
	public FormatData(String[] line)
	{
		this.userId = line[0];
		this.songId = line[1];
		this.nbPlay = new Integer(line[2]);
		
	}

	public String getUserId() {
		return userId;
	}

	public String getSongId() {
		return songId;
	}

	public Integer getNbPlay() {
		return nbPlay;
	}
	
	public Integer getNbPlaySong(String song_id){
		return this.songId.equals(song_id) ? this.nbPlay : 0;
	}

	public Integer getNbPlaySongAndUser(String song_id, String user_id) {
		return (this.songId.equals(song_id) && this.userId.equals(user_id)) ? this.nbPlay : 0;
	}
	
}
