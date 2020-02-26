/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * ConfirmUserDTO.java Created On: Feb 22, 2020 Created By: M1026329
 */
@JsonPropertyOrder("message")
public class ConfirmUserDTO {

    /**
     * userName
     */
    private String userName;
    /**
     * role
     */
    private String role;

    /**
     * message
     */
    private String result;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the message
     */
    public String getResult() {
        return result;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setResult(String result) {
        this.result = result;
    }

}
