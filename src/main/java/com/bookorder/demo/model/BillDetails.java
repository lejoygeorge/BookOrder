package com.bookorder.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDetails {
    private double originalPrice;
    private double finalPrice;
    private double discount;
}
