package com.bookorder.demo.service;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookBasketTest {

    @Test
    void testIsEmpty_whenAllCountsAreZero_shouldReturnTrue() {
        BookBasket basket = new BookBasket(List.of(0, 0, 0, 0, 0));
        assertTrue(basket.isEmpty(), "Basket is empty if all counts equal zero.");
    }

    @Test
    void testIsEmpty_whenSomeCountsAreNonZero_shouldReturnFalse() {
        BookBasket basket = new BookBasket(List.of(0, 1, 0, 0, 0));
        assertFalse(basket.isEmpty(), "Basket is not empty if at least one book count is greater than 0");
    }

    @Test
    void testIsEmpty_whenAllCountsAreNonZero_shouldReturnFalse() {
        BookBasket basket = new BookBasket(List.of(1, 2, 3, 1, 1));

        assertFalse(basket.isEmpty(), "Basket is not empty when all book counts are greater than 0");
    }

    @Test
    void testIsEmpty_whenListIsCompletelyEmpty_shouldReturnTrue() {
        BookBasket basket = new BookBasket(List.of());
        assertTrue(basket.isEmpty(), "Basket is empty if we have empty list");
    }

    @Test
    void testRecordEquality() {
        BookBasket basket1 = new BookBasket(List.of(1, 2, 3));
        BookBasket basket2 = new BookBasket(List.of(1, 2, 3));
        BookBasket basket3 = new BookBasket(List.of(1, 0, 3));
        assertEquals(basket1, basket2, "Identical lists should evaluate as equal");
        assertNotEquals(basket1, basket3, "Different lists should not evaluate as equal");
    }
}