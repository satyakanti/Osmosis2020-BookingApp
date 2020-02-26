/**
 * 
 */
package com.mindtree.minto.dto;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author M1026334
 *
 */
public class FlightItinerary {
	private LinkedHashSet<String> airlineCodes;
	private LinkedHashSet<String> airlineNames;
	private String originPoint;
	private String originCountry;
	private String originCity;
	private String departureTime;
	private Integer departureOffset;
	private String destinationPoint;
	private String destinationCountry;
	private String destinationCity;
	private String arrivalTime;
	private Integer arrivalOffset;
	private Integer journeyDuration;
	private List<Integer> layoverPorts;
	private List<Integer> layoverDurations;
	private List<String> layoverCities;
	private List<String> layoverAirportNames;
	private List<FlightInfo> flightInfos;
	
	/**
	 * @return the arrivalOffset
	 */
	public Integer getArrivalOffset() {
		return arrivalOffset;
	}
	/**
	 * @param arrivalOffset the arrivalOffset to set
	 */
	public void setArrivalOffset(Integer arrivalOffset) {
		this.arrivalOffset = arrivalOffset;
	}
	/**
	 * @return the airlineCodes
	 */
	public LinkedHashSet<String> getAirlineCodes() {
		return airlineCodes;
	}
	/**
	 * @param airlineCodes the airlineCodes to set
	 */
	public void setAirlineCodes(LinkedHashSet<String> airlineCodes) {
		this.airlineCodes = airlineCodes;
	}
	/**
	 * @return the airlineNames
	 */
	public LinkedHashSet<String> getAirlineNames() {
		return airlineNames;
	}
	/**
	 * @param airlineNames the airlineNames to set
	 */
	public void setAirlineNames(LinkedHashSet<String> airlineNames) {
		this.airlineNames = airlineNames;
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
	 * @return the originCity
	 */
	public String getOriginCity() {
		return originCity;
	}
	/**
	 * @param originCity the originCity to set
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
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
	 * @return the departureOffset
	 */
	public Integer getDepartureOffset() {
		return departureOffset;
	}
	/**
	 * @param departureOffset the departureOffset to set
	 */
	public void setDepartureOffset(Integer departureOffset) {
		this.departureOffset = departureOffset;
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
	 * @return the destinationCity
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
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
	 * @return the journeyDuration
	 */
	public Integer getJourneyDuration() {
		return journeyDuration;
	}
	/**
	 * @param journeyDuration the journeyDuration to set
	 */
	public void setJourneyDuration(Integer journeyDuration) {
		this.journeyDuration = journeyDuration;
	}
	/**
	 * @return the layoverPorts
	 */
	public List<Integer> getLayoverPorts() {
		return layoverPorts;
	}
	/**
	 * @param layoverPorts the layoverPorts to set
	 */
	public void setLayoverPorts(List<Integer> layoverPorts) {
		this.layoverPorts = layoverPorts;
	}
	/**
	 * @return the layoverDurations
	 */
	public List<Integer> getLayoverDurations() {
		return layoverDurations;
	}
	/**
	 * @param layoverDurations the layoverDurations to set
	 */
	public void setLayoverDurations(List<Integer> layoverDurations) {
		this.layoverDurations = layoverDurations;
	}
	/**
	 * @return the layoverCities
	 */
	public List<String> getLayoverCities() {
		return layoverCities;
	}
	/**
	 * @param layoverCities the layoverCities to set
	 */
	public void setLayoverCities(List<String> layoverCities) {
		this.layoverCities = layoverCities;
	}
	/**
	 * @return the layoverAirportNames
	 */
	public List<String> getLayoverAirportNames() {
		return layoverAirportNames;
	}
	/**
	 * @param layoverAirportNames the layoverAirportNames to set
	 */
	public void setLayoverAirportNames(List<String> layoverAirportNames) {
		this.layoverAirportNames = layoverAirportNames;
	}
	/**
	 * @return the flightInfos
	 */
	public List<FlightInfo> getFlightInfos() {
		return flightInfos;
	}
	/**
	 * @param flightInfos the flightInfos to set
	 */
	public void setFlightInfos(List<FlightInfo> flightInfos) {
		this.flightInfos = flightInfos;
	}

}
