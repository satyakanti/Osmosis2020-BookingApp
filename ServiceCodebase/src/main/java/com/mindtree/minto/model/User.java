/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

/**
 * 
 * User.java Created On: Feb 22, 2020 Created By: M1026329
 */
@Entity(name = "USER_INFORMATION")
public class User {

    /**
     * userId
     */
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Integer userId;
    /**
     * email
     */
    @Email
//    @NotNull(message = "Email is a required field")
    @Column(name = "EMAIL_ID", unique = true)
    private String email;
    /**
     * role
     */
//    @NotNull(message = "Role is a required field")
    @Column(name = "USER_ROLE")
    private String userRole;
    /**
     * faceID
     */
    @Column(name = "FACE_ID")
    private String faceID;
    /**
     * password
     */
//    @NotNull(message = "Password is a required field")
    @Column(name = "USER_PASS")
    private String pswrd;
    /**
     * walletID
     */
//    @NotNull(message = "WalletID is a required field")
    @Column(name = "WALLET_ID")
    private String walletID;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column(name = "CONTACT_NO")
    private String contact;
    
    @OneToMany(
    		mappedBy="user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<TravelInfo> travelInfos;
    
    @OneToMany(
    		mappedBy="user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<Traveller> travellers;

    @Temporal(TemporalType.DATE)
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    

    public Set<TravelInfo> getTravelInfos() {
		return travelInfos;
	}

	public void setTravelInfos(Set<TravelInfo> travelInfos) {
		this.travelInfos = travelInfos;
	}

	/**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
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

	/**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
        this.email = email;
    }

    /**
     * @return the faceID
     */
    public String getFaceID() {
        return faceID;
    }

    /**
     * @param faceID
     *            the faceID to set
     */
    public void setFaceID(String faceID) {
        this.faceID = faceID;
    }

    /**
     * @return the password
     */
    public String getPswrd() {
        return pswrd;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPswrd(String password) {
        this.pswrd = password;
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
     * @return the registrationDate
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate
     *            the registrationTimeStamp to set
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

	public Set<Traveller> getTravellers() {
		return travellers;
	}

	public void setTravellers(Set<Traveller> travellers) {
		this.travellers = travellers;
	}
	
    
}
