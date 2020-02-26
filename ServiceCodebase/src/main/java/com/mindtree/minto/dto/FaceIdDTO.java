/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 
 * FaceIdDTO.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class FaceIdDTO {

    @NotNull
    @Email(message = "Email is a required field")
    private String email;

    @NotNull
    private String faceId;

    /**
     * @return the faceID
     */
    public String getFaceId() {
        return faceId;
    }

    /**
     * @param faceID
     *            the faceID to set
     */
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
        this.email = email;
    }

}
