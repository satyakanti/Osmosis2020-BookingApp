/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

/**
 * 
 * ConfirmBalance.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class ConfirmBalance {

    private String balance;

    /**
     * @param string
     */
    public ConfirmBalance(String string) {
        this.balance = string;
    }

    /**
     * @return the balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

}
