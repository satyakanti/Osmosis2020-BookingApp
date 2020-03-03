/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.mindtree.minto.util.CommonUtil;
import com.mindtree.minto.util.PasswordEncryptionUtil;

/**
 * 
 * UserDTO.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class BaseUserDTO {
    /**
     * email
     */
    @NotNull
    @Email(message = "Email is a required field")
    private String email;
    /**
     * role
     */
    //@NotNull(message = "Role is a required field")
    private String userRole;
    /**
     * faceID
     */
    private String faceId;
    /**
     * password
     */
    @NotNull(message = "Password is a required field")
    private String password;
    /**
     * walletID
     */
    //@NotNull(message = "WalletID is a required field")
    private String walletID;
    
    /**
     * firstName.
     */
    private String firstName;
    /**
     * lastName.
     */
    private String lastName;
    /**
     * dateOfBirth.
     */
    private Date dateOfBirth;
    /**
     * contact.ss
     */
    private String contact;
    
    public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = CommonUtil.toUpperCase(firstName);
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = CommonUtil.toUpperCase(lastName);
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
     * @return the faceId
     */
    public String getFaceId() {
        return faceId;
    }

    /**
     * @param faceID
     *            the faceId to set
     */
    public void setFaceId(String faceID) {
        this.faceId = faceID;
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
    }

    /**
     * @return the walletID
     */
    public String getWalletID() {
        return walletID;
    }

    /**
     * @param walletID
     *            the walletID to set
     */
    public void setWalletID(String walletID) {
        this.walletID = walletID;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole
     *            the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
