/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

/**
 * 
 * TransactionResultSet.java Created On: Feb 22, 2020 Created By: M1026329
 */
public class TransactionResultSet {
    private String totalCount;

    private Transactions[] transactions;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public Transactions[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transactions[] transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "ClassPojo [totalCount = " + totalCount + ", transactions = " + transactions + "]";
    }
}
