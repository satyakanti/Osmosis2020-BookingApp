package com.mindtree.minto.dto;

import java.util.Date;

import com.mindtree.minto.util.CommonUtil;

public class TravellerDTO {
	
    private Integer travellerId;
	
    private String firstName;
    
    private String lastName;
    
    private Date dateOfBirth;
    
    private String contact;
    
    private Date issuingDate;
    
    private Date expiryDate;
    
    private String passportNo;
    
    private String gender;
   
    private String issuingCountry;
    
	public Integer getTravellerId() {
		return travellerId;
	}

	public void setTravellerId(Integer travellerId) {
		this.travellerId = travellerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = CommonUtil.toUpperCase(firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = CommonUtil.toUpperCase(lastName);
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

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

	public void setIissuingCountry(String issuingCountry) {
		this.issuingCountry = CommonUtil.toUpperCase(issuingCountry);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((travellerId == null) ? 0 : travellerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravellerDTO other = (TravellerDTO) obj;
		if (travellerId == null) {
			if (other.travellerId != null)
				return false;
		} else if (!travellerId.equals(other.travellerId))
			return false;
		return true;
	}
    
	
}
