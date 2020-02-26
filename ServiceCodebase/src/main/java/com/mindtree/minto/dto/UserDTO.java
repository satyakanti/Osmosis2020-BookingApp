package com.mindtree.minto.dto;

import java.util.Date;
import java.util.List;

public class UserDTO extends BaseUserDTO {
	private Integer userId;
	private Date registeredDate;
	private List<TravelInfoDTO> travelInfos;
	private List<TravellerDTO> travellers;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public List<TravelInfoDTO> getTravelInfos() {
		return travelInfos;
	}
	public void setTravelInfos(List<TravelInfoDTO> travelInfos) {
		this.travelInfos = travelInfos;
	}
	public List<TravellerDTO> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<TravellerDTO> travellers) {
		this.travellers = travellers;
	}
	
}
