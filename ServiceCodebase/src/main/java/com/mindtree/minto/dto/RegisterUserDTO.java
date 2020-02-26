/**
 * 
 */
package com.mindtree.minto.dto;

/**
 * @author M1026329
 *
 */
public class RegisterUserDTO extends BaseUserDTO{

	private TravellerDTO primaryUser;

	/**
	 * @return the primaryUser
	 */
	public TravellerDTO getPrimaryUser() {
		return primaryUser;
	}

	/**
	 * @param primaryUser the primaryUser to set
	 */
	public void setPrimaryUser(TravellerDTO primaryUser) {
		this.primaryUser = primaryUser;
	}
	
    
}
