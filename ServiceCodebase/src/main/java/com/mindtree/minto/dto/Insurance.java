package com.mindtree.minto.dto;

import lombok.Data;

@Data
public class Insurance {

	private Details insurance;

	public String getInfo() {
		return insurance.getVendor() + " - " + insurance.getType();
	}
	
}
