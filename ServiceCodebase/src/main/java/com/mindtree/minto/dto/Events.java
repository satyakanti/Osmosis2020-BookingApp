/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * Events.java Created On: Feb 22, 2020 Created By: M1026329
 */
@JsonPropertyOrder({ "eventSignature", "from", "to", "value" })
public class Events {
    private String eventSignature;

    private String from;

    private String to;

    private String value;

    public String getEventSignature() {
        return eventSignature;
    }

    public void setEventSignature(String eventSignature) {
        this.eventSignature = eventSignature;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ClassPojo [eventSignature = " + eventSignature + ", from = " + from + ", to = " + to + ", value = "
            + value + "]";
    }
}
