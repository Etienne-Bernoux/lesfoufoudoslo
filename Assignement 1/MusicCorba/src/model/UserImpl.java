package model;


import profileapp.Song;
import profileapp.User;

public class UserImpl extends User {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6994498448200494145L;
	private static final int SIZE_SONG = 10000;

	
	public UserImpl(String id)
	{
		super();
		this.id = id;
		this.songs = new Song[SIZE_SONG];
	}
	
	public UserImpl(String id, Song[] songs)
	{
		super();
		this.id = id;
		this.songs = songs;
	}

	public void updateSong(Song song)
	{
		boolean find = false;
		boolean end = false;
		int i = 0;
		for(i = 0; i < this.songs.length && !find && !end; i ++){
			System.out.println("this.songs["+ i+"] = "+ this.songs[i] );
			
			if(this.songs[i] == null)
				end = true;
			else
			{
				if(this.songs[i].id.equals(song))
				{
					this.songs[i].play_count +=  song.play_count;
					find = true;
				}
			}
		}
		// It is 
		if( !find)
		{
			System.out.println("this.songs["+ i+"] <= "+ song );
			this.songs[i-1] = song;
		}
	}
	
	public Integer getNbPlaySong(String songId)
	{
		boolean again = true;
		for(int i = 0; again; i ++){
			System.out.println("+1 " + songId);
			if(this.songs[i] == null)
			{
				again = false;
			}
			else
			{
				if(this.songs[i].id.equals(songId))
				{
					return this.songs[i].play_count;
				}
			}
		}
		System.out.println("UserImpl.getNbPlaySong() => return 0");
		return new Integer(0);
	}
	
	public Integer getNbPlay()
	{
		Integer res = new Integer(0);
		for (Song s : this.songs)
		{
			if(s == null)
				return res;
			res += s.play_count;
		}
		return res;
	}
	
	


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserImpl)) {
			return false;
		}
		UserImpl other = (UserImpl) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", songs=" + songs + "]";
	}
	
	
	
}
