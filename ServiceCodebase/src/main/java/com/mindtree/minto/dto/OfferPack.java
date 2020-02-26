/**
 * 
 */
package com.mindtree.minto.dto;

/**
 * @author M1026334
 *
 */
public class OfferPack {
	private FlightItinerary onwardFlightItinerary;
	private FlightItinerary ReturnFlightItinerary;
	private Fare fare;

	/**
	 * @return the onwardFlightItinerary
	 */
	public FlightItinerary getOnwardFlightItinerary() {
		return onwardFlightItinerary;
	}

	/**
	 * @param onwardFlightItinerary the onwardFlightItinerary to set
	 */
	public void setOnwardFlightItinerary(FlightItinerary onwardFlightItinerary) {
		this.onwardFlightItinerary = onwardFlightItinerary;
	}

	/**
	 * @return the returnFlightItinerary
	 */
	public FlightItinerary getReturnFlightItinerary() {
		return ReturnFlightItinerary;
	}

	/**
	 * @param returnFlightItinerary the returnFlightItinerary to set
	 */
	public void setReturnFlightItinerary(FlightItinerary returnFlightItinerary) {
		ReturnFlightItinerary = returnFlightItinerary;
	}

	/**
	 * @return the fare
	 */
	public Fare getFare() {
		return fare;
	}

	/**
	 * @param fare the fare to set
	 */
	public void setFare(Fare fare) {
		this.fare = fare;
	}
	
	
}
