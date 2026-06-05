package com.bookorder.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptimalDiscountPricingStrategyTest {

    private OptimalDiscountPricingStrategy pricingStrategy;

    @BeforeEach
    void setUp() {
        pricingStrategy = new OptimalDiscountPricingStrategy();
    }

    @Test
    void testEmptyBasket_ReturnsZero() {
        List<Integer> quantities = List.of(0, 0, 0, 0, 0);
        assertEquals(0.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    void testSingleBook_NoDiscount() {
        List<Integer> quantities = List.of(1, 0, 0, 0, 0);
        assertEquals(50.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    void testTwoSameBooks_NoDiscount() {
        List<Integer> quantities = List.of(2, 0, 0, 0, 0);
        assertEquals(100.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    void testSimpleDiscounts_AllDistinct() {
        // 5% discount
        assertEquals(95.0, pricingStrategy.calculateMinimumPrice(List.of(1, 1, 0, 0, 0)), 0.001);
        // 10% discount
        assertEquals(135.0, pricingStrategy.calculateMinimumPrice(List.of(1, 1, 1, 0, 0)), 0.001);
        // 20% discount
        assertEquals(160.0, pricingStrategy.calculateMinimumPrice(List.of(1, 1, 1, 1, 0)), 0.001);
        // 25% discount
        assertEquals(187.5, pricingStrategy.calculateMinimumPrice(List.of(1, 1, 1, 1, 1)), 0.001);
    }

    @Test
    void testMixedBooks_SimpleGrouping() {
        List<Integer> quantities = List.of(2, 1, 0, 0, 0);
        assertEquals(145.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    void testComplexEdgeCase_FourPlusFourIsCheaperThanFivePlusThree() {
        List<Integer> quantities = List.of(2, 2, 2, 1, 1);
        assertEquals(320.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    void testLargeBasket() {
        List<Integer> quantities = List.of(5, 5, 5, 5, 5);
        assertEquals(937.5, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }
}