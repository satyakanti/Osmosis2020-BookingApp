/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mindtree.minto.util.CommonUtil;

/**
 * 
 * Transactions.java Created On: Feb 22, 2020 Created By: M1026329
 */
@JsonPropertyOrder({ "index", "hash", "blockHash", "blockNumber", "timestamp", "status", "isPrivate", "from", "to",
        "events", "isERC20" })
public class Transactions {
	
	static String dateFormat = "yyyy-MM-dd";
	
    private String blockHash;
 
    private String isPrivate;

    private String isERC20;

    private String blockNumber;

    private String index;

    private String from;

    private String to;

    private String hash;

    private Events[] events;

    private String timestamp;

    private String status;
    
    public Date getDate() {
    	return CommonUtil.getDate(timestamp, dateFormat);
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getIsERC20() {
        return isERC20;
    }

    public void setIsERC20(String isERC20) {
        this.isERC20 = isERC20;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Events[] getEvents() {
        return events;
    }

    public void setEvents(Events[] events) {
        this.events = events;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [blockHash = " + blockHash + ", private = " + isPrivate + ", isERC20 = " + isERC20
            + ", blockNumber = " + blockNumber + ", index = " + index + ", from = " + from + ", to = " + to
            + ", hash = " + hash + ", events = " + events + ", timestamp = " + timestamp + ", status = " + status + "]";
    }

    /**
     * @return the isPrivate
     */
    public String getIsPrivate() {
        return isPrivate;
    }

    /**
     * @param isPrivate
     *            the isPrivate to set
     */
    @JsonProperty("Private")
    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }
}
