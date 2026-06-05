package com.example.demo.service;

import com.example.demo.model.BookItem;
import com.example.demo.model.BooksEnum;
import com.example.demo.model.BillDetails;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceCalculatorService {

    private final PricingStrategy pricingStrategy;

    public PriceCalculatorService(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public BillDetails calculatePrice(List<BookItem> purchasedItems) {
        double totalOriginalPrice = purchasedItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getBook().getPrice())
                .sum();
        List<Integer> quantities = purchasedItems.stream()
                .collect(Collectors.groupingBy(
                        BookItem::getBook,
                        () -> new EnumMap<>(BooksEnum.class),
                        Collectors.summingInt(BookItem::getQuantity)
                ))
                .values()
                .stream()
                .toList();
        double totalFinalPrice = pricingStrategy.calculateMinimumPrice(quantities);
        double totalDiscount = totalOriginalPrice - totalFinalPrice;
        var receipt = new BillDetails();
        receipt.setOriginalPrice(totalOriginalPrice);
        receipt.setFinalPrice(totalFinalPrice);
        receipt.setDiscount(totalDiscount);
        return receipt;
    }
}