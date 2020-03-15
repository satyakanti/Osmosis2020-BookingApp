package com.mindtree.minto.dto;

import lombok.Data;

@Data
public class Detail {
    private DetailType type;
    private String info;
    private double basicPrice;
    private double tax;
    private double totalPrice;
}
