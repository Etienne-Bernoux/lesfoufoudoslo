package model;

import profileapp.Song;

public class SongImpl extends Song {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4068150737573058614L;



	public SongImpl(String songId, Integer playCount) {
		super();
		this.id = songId;
		this.play_count = playCount;
	}
	
	
	
	/** Getteur/ Setteur **/
	public Integer getPlayCount() {
		return play_count;
	}
	public String getSongId() {
		return id;
	}

	public void addPlayCount(Integer count) {
		this.play_count += count;
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
		if (!(obj instanceof SongImpl)) {
			return false;
		}
		SongImpl other = (SongImpl) obj;
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
