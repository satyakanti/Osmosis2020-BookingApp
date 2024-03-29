/**
 * 
 */
package com.mindtree.minto.dto;

import javax.validation.constraints.NotNull;

import com.mindtree.minto.util.CommonUtil;

/**
 * @author M1026329
 *
 */
public class PackageBookingDTO {

	@NotNull(message = "partner is a required field")
	String partner;
	@NotNull(message = "amount is a required field")
	int amount;
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = CommonUtil.toUpperCase(partner);
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
