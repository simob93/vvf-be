package it.vvfriva.models;
/**
 * 
 * @author simone
 *
 */
public class ModelInfoVigili {
	private Integer active;
	private Integer deActive;
	private Integer waiting;
	private Integer total;
	
	public ModelInfoVigili() {}

	/**
	 * @return the active
	 */
	public Integer getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Integer active) {
		this.active = active;
	}

	/**
	 * @return the deActive
	 */
	public Integer getDeActive() {
		return deActive;
	}

	/**
	 * @param deActive the deActive to set
	 */
	public void setDeActive(Integer deActive) {
		this.deActive = deActive;
	}

	/**
	 * @return the waiting
	 */
	public Integer getWaiting() {
		return waiting;
	}

	/**
	 * @param waiting the waiting to set
	 */
	public void setWaiting(Integer waiting) {
		this.waiting = waiting;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
