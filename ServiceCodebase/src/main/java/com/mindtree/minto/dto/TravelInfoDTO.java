/**
 * 
 */
package com.mindtree.minto.dto;

public class TravelInfoDTO {

    private Integer travelId;
    
    private String travelInfo;

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public String getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(String travelInfo) {
		this.travelInfo = travelInfo;
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
		TravelInfoDTO other = (TravelInfoDTO) obj;
		if (travelId == null) {
			if (other.travelId != null)
				return false;
		} else if (!travelId.equals(other.travelId))
			return false;
		return true;
	}
    
}
