/**
 * 
 */
package com.mindtree.minto.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mindtree.minto.util.CommonUtil;

/**
 * @author M1026334
 *
 */

public class FlightInfo {
	 private String airlineCode;
	 private String aircraft;
	 private String airlineName;
	 private String flightNo;
	 private String originPoint;
	 private String originCountry;
	 private String originAirportName;
	 private Date departureDate;
	 private String departureTime;
	 private Integer departureOffset;
	 private String destinationPoint;
	 private String destinationCountry;
	 private String destinationAirportName;
	 private Date arrivalDate;
	 private String arrivalTime;
	 private String arrivalOffset;
	 private String flightDuration;
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}
	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}
	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * @param airlineName the airlineName to set
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}
	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	/**
	 * @return the originPoint
	 */
	public String getOriginPoint() {
		return originPoint;
	}
	/**
	 * @param originPoint the originPoint to set
	 */
	public void setOriginPoint(String originPoint) {
		this.originPoint = originPoint;
	}
	/**
	 * @return the originCountry
	 */
	public String getOriginCountry() {
		return originCountry;
	}
	/**
	 * @param originCountry the originCountry to set
	 */
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	/**
	 * @return the originAirportName
	 */
	public String getOriginAirportName() {
		return originAirportName;
	}
	/**
	 * @param originAirportName the originAirportName to set
	 */
	public void setOriginAirportName(String originAirportName) {
		this.originAirportName = originAirportName;
	}
	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}
	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	
	/**
	 * @return the destinationPoint
	 */
	public String getDestinationPoint() {
		return destinationPoint;
	}
	/**
	 * @param destinationPoint the destinationPoint to set
	 */
	public void setDestinationPoint(String destinationPoint) {
		this.destinationPoint = destinationPoint;
	}
	/**
	 * @return the destinationCountry
	 */
	public String getDestinationCountry() {
		return destinationCountry;
	}
	/**
	 * @param destinationCountry the destinationCountry to set
	 */
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	/**
	 * @return the destinationAirportName
	 */
	public String getDestinationAirportName() {
		return destinationAirportName;
	}
	/**
	 * @param destinationAirportName the destinationAirportName to set
	 */
	public void setDestinationAirportName(String destinationAirportName) {
		this.destinationAirportName = destinationAirportName;
	}
	
	/**
	 * @return the departureDate
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	/**
	 * @param departureDate the departureDate to set
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}
	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @return the arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the departureOffset
	 */
	public Integer getDepartureOffset() {
		return departureOffset;
	}
	/**
	 * @return the flightDuration
	 */
	public String getFlightDuration() {
		return flightDuration;
	}
	/**
	 * @param flightDuration the flightDuration to set
	 */
	public void setFlightDuration(String flightDuration) {
		this.flightDuration = flightDuration;
	}
	/**
	 * @param departureOffset the departureOffset to set
	 */
	public void setDepartureOffset(Integer departureOffset) {
		this.departureOffset = departureOffset;
	}
	/**
	 * @return the arrivalOffset
	 */
	public String getArrivalOffset() {
		return arrivalOffset;
	}
	/**
	 * @param arrivalOffset the arrivalOffset to set
	 */
	public void setArrivalOffset(String arrivalOffset) {
		this.arrivalOffset = arrivalOffset;
	}
	
	public String getInfo() {
		return airlineCode+flightNo + " " + departureTime + " " + originPoint +  " " + CommonUtil.formatDate(departureDate);
	}
	 
	@Override
	public String toString() {
		return airlineCode+flightNo + "-" + getDepDate() + "-" + originPoint + "-" + destinationPoint;
	}
	/**
	 * @return
	 */
	public String getDepDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
		String depDate = sdf.format(departureDate);
		return depDate;
	}
}
