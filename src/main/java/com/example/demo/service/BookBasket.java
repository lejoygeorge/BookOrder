package com.example.demo.service;

import java.util.List;

public record BookBasket(List<Integer> counts) {
    public boolean isEmpty() {
        return counts.stream().allMatch(count -> count == 0);
    }
}