package model;

import java.util.HashMap;
import java.util.Map;

public class User {

	private final String id;
	private final Map<String, Song> songs;
	
	
	public User(String id) {
		super();
		this.id = id;
		this.songs = new HashMap<String, Song>();
	}

	public void updateSong(Song song)
	{
		Song s = this.songs.get(song.getSongId());
		if( s != null)
		{
			song.addPlayCount(s.getPlayCount());
		}
		this.songs.put(song.getSongId(), song);
	}
	
	
	/** Getters **/
	public String getId() {
		return id;
	}
	public Map<String, Song> getSongs() {
		return songs;
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
	
}
