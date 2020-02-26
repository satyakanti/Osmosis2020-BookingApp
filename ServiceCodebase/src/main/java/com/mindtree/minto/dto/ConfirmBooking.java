/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

/**
 * 
 * ConfirmBooking.java Created On:Feb 22, 2020 Created By: M1026329
 */
public class ConfirmBooking {

    private String message;

    /**
     * @param message
     */
    public ConfirmBooking(String message) {
        super();
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
