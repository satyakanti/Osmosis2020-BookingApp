/**
 * 
 */
package com.mindtree.minto.dto;

/**
 * @author M1026334
 *
 */
public class Fare {
	private Double baseFare;
	private Double taxes;
    private Double total;
	/**
	 * @return the baseFare
	 */
	public Double getBaseFare() {
		return baseFare;
	}
	/**
	 * @param baseFare the baseFare to set
	 */
	public void setBaseFare(Double baseFare) {
		this.baseFare = baseFare;
	}
	/**
	 * @return the taxes
	 */
	public Double getTaxes() {
		return taxes;
	}
	/**
	 * @param taxes the taxes to set
	 */
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fare [baseFare=" + baseFare + ", taxes=" + taxes + ", total=" + total + "]";
	}
	
}
