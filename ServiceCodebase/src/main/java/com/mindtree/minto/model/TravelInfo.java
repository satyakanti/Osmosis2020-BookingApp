/**
 * 
 */
package com.mindtree.minto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * 
 * TravelInfo.java Created On: Feb 24, 2020 Created By: M1026329
 */
@Entity(name = "TRAVEL_INFORMATION")
public class TravelInfo {

	/**
     * travelId
     */
    @Id
    @GeneratedValue
    @Column(name = "TRAVE_ID")
    private Integer travelId;
    
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;
    
    /**
     * travelInfo
     */
    @Lob
    @Column(name = "TRAVEL_INFO", columnDefinition="BLOB")
    private byte[] travelInfo;

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public byte[] getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(byte[] travelInfo) {
		this.travelInfo = travelInfo;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((travelId == null) ? 0 : travelId.hashCode());
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
		TravelInfo other = (TravelInfo) obj;
		if (travelId == null) {
			if (other.travelId != null)
				return false;
		} else if (!travelId.equals(other.travelId))
			return false;
		return true;
	}
    
}
