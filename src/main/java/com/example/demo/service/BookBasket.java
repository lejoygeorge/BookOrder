package com.example.demo.service;

import java.util.Arrays;
import java.util.Collections;

public record BookBasket(Integer[] counts) {
    public BookBasket {
        Arrays.sort(counts, Collections.reverseOrder());
    }

    public boolean isEmpty() {
        return Arrays.stream(counts).allMatch(count -> count == 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BookBasket other && Arrays.equals(counts, other.counts);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(counts);
    }
}
