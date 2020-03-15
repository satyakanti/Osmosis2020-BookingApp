/**
 * 
 */
package com.mindtree.minto.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author M1026334
 *
 */

public class SearchFlightResponse {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<OfferPack> offerPackages;

	/**
	 * @return the offerPackages
	 */
	public List<OfferPack> getOfferPackages() {
		return offerPackages;
	}

	/**
	 * @param offerPackages the offerPackages to set
	 */
	public void setOfferPackages(List<OfferPack> offerPackages) {
		this.offerPackages = offerPackages;
	}
	
	
}
