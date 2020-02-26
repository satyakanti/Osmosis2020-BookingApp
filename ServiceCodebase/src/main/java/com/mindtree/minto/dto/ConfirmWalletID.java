/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

/**
 * 
 * ConfirmWalletID.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class ConfirmWalletID {

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    /**
     * @param string
     */
    public ConfirmWalletID(String string) {
        this.address = string;
    }

}
