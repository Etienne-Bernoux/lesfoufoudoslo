package model;

public class Song {

	private final String songId;
	private Integer playCount = null;
	
	public Song(String songId, Integer playCount) {
		super();
		this.songId = songId;
		this.playCount = playCount;
	}
	
	
	
	/** Getteur/ Setteur **/
	public Integer getPlayCount() {
		return playCount;
	}
	public String getSongId() {
		return songId;
	}

	public void addPlayCount(Integer count) {
		this.playCount += count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((songId == null) ? 0 : songId.hashCode());
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
		if (!(obj instanceof Song)) {
			return false;
		}
		Song other = (Song) obj;
		if (songId == null) {
			if (other.songId != null) {
				return false;
			}
		} else if (!songId.equals(other.songId)) {
			return false;
		}
		return true;
	}
	
	
	
	
}
