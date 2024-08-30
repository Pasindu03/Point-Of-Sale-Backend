package com.example.posb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto implements Serializable {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
}
