/**
 * 
 */
package com.mindtree.minto.dto;

import java.util.Date;

import lombok.Data;

/**
 * 
 * TravelInfo.java Created On: Feb 24, 2020 Created By: M1026329
 */
@Data
public class TransactionReport {

    private String txnId;
    
    private String merchant;
    
    private Date date;

    private String amount;
    
    private boolean expenseFound;
    
    private boolean txnFound;

}
