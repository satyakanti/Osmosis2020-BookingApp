package com.mindtree.minto.dto;

import com.mindtree.minto.util.CommonUtil;

import lombok.Data;

@Data
public class Hotel {
	
	private Details hotel;
	private HotelRequest req;
	
	public String getInfo() {
		return hotel.getName() + ", " + hotel.getCity() + ", " + CommonUtil.formatDate(req.getCheckInDate());
	}
	
}
