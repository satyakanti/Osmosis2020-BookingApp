package com.mindtree.minto.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Invoice {
    private String date;
    private double totalPrice;
    private String paymentMode;
    private List<Detail> details = new ArrayList<>();
    private String invoiceNo;
    private String travelId;
    private String bookedBy;
    private String email;
    private String contact;
    private String txnId;

}
