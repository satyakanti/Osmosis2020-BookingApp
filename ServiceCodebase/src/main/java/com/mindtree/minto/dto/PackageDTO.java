package com.mindtree.minto.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.mindtree.minto.util.CommonUtil;

public class PackageDTO {

	
    @NotNull
    @Email(message = "Email is a required field")
    private String email;
    
    @NotNull(message = "faceId is a required field")
    private String faceId;
    
	//@NotNull
	//@NotEmpty(message = "bookings is a required field")
    private List<PackageBookingDTO> bookings;
	
	//@NotNull(message = "travelInfo is a required field")
    private String travelInfo;
	
	//@NotNull(message = "flight is a required field")
    private Flight flight;
    
    private Integer total;
    
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		if (total == null || total == 0) {
			total = 0;
			for (PackageBookingDTO booking : bookings) {
				total += booking.getAmount();
			}
		}
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the faceId
	 */
	public String getFaceId() {
		return faceId;
	}

	/**
	 * @param faceId the faceId to set
	 */
	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	/**
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * @param flight the flight to set
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(String travelInfo) {
		this.travelInfo = travelInfo;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = CommonUtil.toLowerCase(email);
	}

	public List<PackageBookingDTO> getBookings() {
		return bookings;
	}

	public void setBookings(List<PackageBookingDTO> bookings) {
		this.bookings = bookings;
	}
	
}
