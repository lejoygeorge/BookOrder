package com.bookorder.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return 0.0 when the basket is completely empty")
    void testEmptyBasket_ReturnsZero() {
        List<Integer> quantities = List.of(0, 0, 0, 0, 0);
        assertEquals(0.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    @DisplayName("Should return the standard price (50.0) for a single book with no discount")
    void testSingleBook_NoDiscount() {
        List<Integer> quantities = List.of(1, 0, 0, 0, 0);
        assertEquals(50.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    @DisplayName("Should return full price (100.0) for two copies of the same book with no discount")
    void testTwoSameBooks_NoDiscount() {
        List<Integer> quantities = List.of(2, 0, 0, 0, 0);
        assertEquals(100.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    @DisplayName("Should correctly apply progressive discounts for groups of distinct books")
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
    @DisplayName("Should calculate the correct price for a mix of grouped and ungrouped identical books")
    void testMixedBooks_SimpleGrouping() {
        List<Integer> quantities = List.of(2, 1, 0, 0, 0);
        assertEquals(145.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    @DisplayName("Edge Case: Should prefer two groups of four (320.0) over a group of five and three (322.5)")
    void testComplexEdgeCase_FourPlusFourIsCheaperThanFivePlusThree() {
        List<Integer> quantities = List.of(2, 2, 2, 1, 1);
        assertEquals(320.0, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }

    @Test
    @DisplayName("Performance: Should quickly calculate the minimum price for a large basket without timing out")
    void testLargeBasket() {
        List<Integer> quantities = List.of(5, 5, 5, 5, 5);
        assertEquals(937.5, pricingStrategy.calculateMinimumPrice(quantities), 0.001);
    }
}