/**
 * 
 */
package com.mindtree.minto.dto;

public class TravelInfoDTO {

    private Integer id;
    
    private String travelInfo;


	public String getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(String travelInfo) {
		this.travelInfo = travelInfo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
