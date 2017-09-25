package model;

public class UserCounter implements Comparable<UserCounter> {
	
	private String id = null;
	private Integer count = null;
	public UserCounter(String id, Integer count) {
		super();
		this.id = id;
		this.count = count;
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	public Integer getCount() {
		return count;
	}


	public void add(UserCounter uc){
		this.count += uc.count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserCounter [id=" + this.id + ", count=" + this.count + "]";
	}


//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}


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
		
		return id.equals(other.id);
	}

	@Override
	public int compareTo(UserCounter o) {
//		return 1 - this.count.compareTo(o.count);{
		return o.count.compareTo(this.count);
	}
	

}
