package model;

import java.util.HashMap;
import java.util.Map;

import profileapp.User;

public class UserImpl extends User {

	private String id = null;
	private Map<String, SongImpl> songs = null;
	
	
	public UserImpl(String id)
	{
		super();
		this.id = id;
		this.songs = new HashMap<String, SongImpl>();
	}

	public void updateSong(SongImpl song)
	{
		SongImpl s = this.songs.get(song.getSongId());
		if( s != null)
		{
			song.addPlayCount(s.getPlayCount());
		}
		this.songs.put(song.getSongId(), song);
	}
	
	public Integer getNbPlaySong(String songId)
	{
		SongImpl s = this.songs.get(songId);
		if (s == null)
			return 0;
		else
			return s.getPlayCount();
	}
	
	public Integer getNbPlay()
	{
		Integer res = new Integer(0);
		for (SongImpl s : this.songs.values())
			res += s.getPlayCount();
		return res;
	}
	
	
	/** Getters **/
	public String getId() {
		return id;
	}
	public Map<String, SongImpl> getSongs() {
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
