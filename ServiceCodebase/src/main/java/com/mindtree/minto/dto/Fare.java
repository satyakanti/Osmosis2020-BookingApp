/**
 * 
 */
package com.mindtree.minto.dto;

/**
 * @author M1026334
 *
 */
public class Fare {
	private Integer baseFare;
	private Integer taxes;
    private Integer total;
	/**
	 * @return the baseFare
	 */
	public Integer getBaseFare() {
		return baseFare;
	}
	/**
	 * @param baseFare the baseFare to set
	 */
	public void setBaseFare(Integer baseFare) {
		this.baseFare = baseFare;
	}
	/**
	 * @return the taxes
	 */
	public Integer getTaxes() {
		return taxes;
	}
	/**
	 * @param taxes the taxes to set
	 */
	public void setTaxes(Integer taxes) {
		this.taxes = taxes;
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
