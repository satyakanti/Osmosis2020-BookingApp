/**
 * 
 */
package com.mindtree.minto.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author M1026329
 *
 */
@JsonInclude(Include.NON_NULL)
public class BookingDTO {
	
	private String email;
	private String firstName;
	private String lastName;
	private Integer numberOfCheckedInBags;
	private String dateOfFirstSegment;
	private String flight;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getNumberOfCheckedInBags() {
		return numberOfCheckedInBags;
	}
	public void setNumberOfCheckedInBags(Integer numberOfCheckedInBags) {
		this.numberOfCheckedInBags = numberOfCheckedInBags;
	}
	public String getDateOfFirstSegment() {
		return dateOfFirstSegment;
	}
	public void setDateOfFirstSegment(String dateOfFirstSegment) {
		this.dateOfFirstSegment = dateOfFirstSegment;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	
}
