package model;

public class UserCounter implements Comparable<UserCounter> {
	
	private final String id;
	private Integer count = null;
	public UserCounter(String id, Integer count) {
		super();
		this.id = id;
		this.count = count;
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
		if (!(obj instanceof UserCounter)) {
			return false;
		}
		UserCounter other = (UserCounter) obj;
		if (count == null) {
			if (other.count != null) {
				return false;
			}
		} else if (!count.equals(other.count)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(UserCounter o) {
		return this.count.compareTo(o.count);
	}
	

}
