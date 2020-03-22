/**
 * 
 */
package com.mindtree.minto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * TravelInfo.java Created On: Feb 24, 2020 Created By: M1026329
 */
@Entity(name = "EXPENSE_INFORMATION")
public class ExpenseInfo {

    @Id
    @GeneratedValue
    @Column(name = "EXPENSE_ID")
    private Integer expenselId;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="TRAVE_ID", nullable=false)
    private TravelInfo travelInfo;
    
    @Column(name = "MERCHANT_NAME")
    private String merchantName;
    
    @Column(name = "TRANSACTION_ID")
    private String trasactionId;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_EXPENSE")
    private Date dateOfExpense;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_CLAIM")
    private Date dateOfClaim;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "AMOUNT")
    private String amount;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getExpenselId() {
		return expenselId;
	}

	public void setExpenselId(Integer expenselId) {
		this.expenselId = expenselId;
	}

	public TravelInfo getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(TravelInfo travelInfo) {
		this.travelInfo = travelInfo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTrasactionId() {
		return trasactionId;
	}

	public void setTrasactionId(String trasactionId) {
		this.trasactionId = trasactionId;
	}

	public Date getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(Date dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

	public Date getDateOfClaim() {
		return dateOfClaim;
	}

	public void setDateOfClaim(Date dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfClaim == null) ? 0 : dateOfClaim.hashCode());
		result = prime * result + ((dateOfExpense == null) ? 0 : dateOfExpense.hashCode());
		result = prime * result + ((expenselId == null) ? 0 : expenselId.hashCode());
		result = prime * result + ((merchantName == null) ? 0 : merchantName.hashCode());
		result = prime * result + ((trasactionId == null) ? 0 : trasactionId.hashCode());
		result = prime * result + ((travelInfo == null) ? 0 : travelInfo.hashCode());
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
		ExpenseInfo other = (ExpenseInfo) obj;
		if (dateOfClaim == null) {
			if (other.dateOfClaim != null)
				return false;
		} else if (!dateOfClaim.equals(other.dateOfClaim))
			return false;
		if (dateOfExpense == null) {
			if (other.dateOfExpense != null)
				return false;
		} else if (!dateOfExpense.equals(other.dateOfExpense))
			return false;
		if (expenselId == null) {
			if (other.expenselId != null)
				return false;
		} else if (!expenselId.equals(other.expenselId))
			return false;
		if (merchantName == null) {
			if (other.merchantName != null)
				return false;
		} else if (!merchantName.equals(other.merchantName))
			return false;
		if (trasactionId == null) {
			if (other.trasactionId != null)
				return false;
		} else if (!trasactionId.equals(other.trasactionId))
			return false;
		if (travelInfo == null) {
			if (other.travelInfo != null)
				return false;
		} else if (!travelInfo.equals(other.travelInfo))
			return false;
		return true;
	}
    
}
