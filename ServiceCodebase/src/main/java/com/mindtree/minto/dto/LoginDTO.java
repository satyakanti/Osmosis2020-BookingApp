/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.mindtree.minto.util.CommonUtil;
import com.mindtree.minto.util.PasswordEncryptionUtil;

/**
 * 
 * LoginStatusDTO.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class LoginDTO {

    /**
     * email
     */
    @NotNull
    @Email
    private String email;
    /**
     * password
     */
    private String password;
    /**
     * faceId
     */
    private String faceId;
    
    public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	/**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = CommonUtil.toLowerCase(email);
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = PasswordEncryptionUtil.hashPassword(password);
        ;
    }

}
