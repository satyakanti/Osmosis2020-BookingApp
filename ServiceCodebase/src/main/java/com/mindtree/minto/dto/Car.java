package com.mindtree.minto.dto;

import com.mindtree.minto.util.CommonUtil;

import lombok.Data;

@Data
public class Car {

	private Details car;
	private CarRequest req;
	
	public String getInfo() {
		return car.getPartner() + (car.get_class() != null ? ", " + car.get_class() : "") + ", " + CommonUtil.formatDate(req.getPickUpDate());
	}
	
}
