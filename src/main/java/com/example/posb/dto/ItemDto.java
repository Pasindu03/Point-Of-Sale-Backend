package com.example.posb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private String itemCode;
    private String itemName;
    private Double itemPrice;
    private Integer itemQuantity;
}
