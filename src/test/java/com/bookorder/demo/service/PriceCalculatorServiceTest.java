package com.bookorder.demo.service;

import com.bookorder.demo.model.BillDetails;
import com.bookorder.demo.model.BookItem;
import com.bookorder.demo.model.BooksEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorServiceTest {

    @Mock
    private PricingStrategy pricingStrategy;

    @InjectMocks
    private PriceCalculatorService priceCalculatorService;

    @Test
    void testCalculatePrice_EmptyBasket() {
        when(pricingStrategy.calculateMinimumPrice(anyList())).thenReturn(0.0);
        BillDetails receipt = priceCalculatorService.calculatePrice(Collections.emptyList());
        assertEquals(0.0, receipt.getOriginalPrice(), 0.001);
        assertEquals(0.0, receipt.getFinalPrice(), 0.001);
        assertEquals(0.0, receipt.getDiscount(), 0.001);
    }

    @Test
    void testCalculatePrice_SingleItem() {
        List<BookItem> items = List.of(new BookItem(BooksEnum.BOOK1, 1));
        when(pricingStrategy.calculateMinimumPrice(anyList())).thenReturn(50.0);
        BillDetails receipt = priceCalculatorService.calculatePrice(items);
        assertEquals(50.0, receipt.getOriginalPrice(), 0.001);
        assertEquals(50.0, receipt.getFinalPrice(), 0.001);
        assertEquals(0.0, receipt.getDiscount(), 0.001);
    }

    @Test
    void testCalculatePrice_MultipleItemsWithGrouping() {
        List<BookItem> items = List.of(
                new BookItem(BooksEnum.BOOK1, 2),
                new BookItem(BooksEnum.BOOK2, 1),
                new BookItem(BooksEnum.BOOK3, 1)
        );
        when(pricingStrategy.calculateMinimumPrice(anyList())).thenReturn(180.0);
        BillDetails receipt = priceCalculatorService.calculatePrice(items);
        assertEquals(200.0, receipt.getOriginalPrice(), 0.001);
        assertEquals(180.0, receipt.getFinalPrice(), 0.001);
        assertEquals(20.0, receipt.getDiscount(), 0.001);
    }
}