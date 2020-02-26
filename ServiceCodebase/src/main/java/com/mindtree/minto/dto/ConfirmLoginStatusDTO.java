/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * LoginStatusDTO.java Created On: Feb 22, 2020 Created By: M1026329
 */
@JsonPropertyOrder("message")
public class ConfirmLoginStatusDTO extends UserDTO {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
