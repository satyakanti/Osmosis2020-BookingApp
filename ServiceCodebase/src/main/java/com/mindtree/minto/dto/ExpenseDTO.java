package com.mindtree.minto.dto;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class ExpenseDTO {

	String document;
	String description;
	@NotNull
	Integer travelId;
	String fileName;
}
