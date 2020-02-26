/**
 * 
 */
package com.mindtree.minto.dto;

import java.util.Date;

/**
 * @author M1026329
 *
 */
public class RegisterUserDTO extends BaseUserDTO{

	/**
     * issuingDate.
     */
    private Date issuingDate;
    /**
     * expiryDate.
     */
    private Date expiryDate;
    /**
     * passportNo.
     */
    private String passportNo;
    /**
     * gender.
     */
    private String gender;
    /**
     * issuingCountry.
     */
    private String issuingCountry;
	public Date getIssuingDate() {
		return issuingDate;
	}
	public void setIssuingDate(Date issuingDate) {
		this.issuingDate = issuingDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIssuingCountry() {
		return issuingCountry;
	}
	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}
    
    
}
