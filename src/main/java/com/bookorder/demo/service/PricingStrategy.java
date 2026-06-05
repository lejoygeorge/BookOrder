package com.bookorder.demo.service;

import java.util.List;

public interface PricingStrategy {
    double calculateMinimumPrice(List<Integer> bookQuantities);
}