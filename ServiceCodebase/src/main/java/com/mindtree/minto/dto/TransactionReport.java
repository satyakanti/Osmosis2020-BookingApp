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

    
    private String merchantName;
    
    private Date dateOfExpense;

    private String amount;
    
    private boolean isExpenseClaimed;
    
    private boolean isTransactionPresent;

}
