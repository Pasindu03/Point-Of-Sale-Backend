package com.example.posb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombineDto implements Serializable {
    private String orderId;
    private String orderDate;
    private String customerId;
    private String itemCode;
    private String description;
    private int orderQty;
    private double price;
}
