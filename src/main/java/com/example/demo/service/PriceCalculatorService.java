package com.example.demo.service;


import com.example.demo.model.BookItem;
import com.example.demo.model.BooksEnum;

import com.example.demo.model.BillDetails;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class PriceCalculatorService {
    private final Map<BookBasket, Double> basketCache = new HashMap<>();

    public BillDetails calculatePrice(List<BookItem> purchasedItems) {
        var bookQuantities = new EnumMap<BooksEnum, Integer>(BooksEnum.class);
        double totalOriginalPrice = 0.0;
        for (var item : purchasedItems) {
            totalOriginalPrice += (item.getQuantity() * item.getBook().getPrice());
            bookQuantities.merge(item.getBook(), item.getQuantity(), Integer::sum);
        }
        Integer[] quantitiesArray = bookQuantities.values().toArray(Integer[]::new);
        double totalFinalPrice = calculateMinimumPrice(new BookBasket(quantitiesArray));
        double totalDiscount = totalOriginalPrice - totalFinalPrice;
        var receipt = new BillDetails();
        receipt.setOriginalPrice(totalOriginalPrice);
        receipt.setFinalPrice(totalFinalPrice);
        receipt.setDiscount(totalDiscount);
        return receipt;
    }

    private double calculateMinimumPrice(BookBasket currentBasket) {
        if (currentBasket.isEmpty()) {
            return 0.0;
        }
        if (basketCache.containsKey(currentBasket)) {
            return basketCache.get(currentBasket);
        }
        int[] distinctBooksPresent = IntStream.range(0, currentBasket.counts().length)
                .filter(index -> currentBasket.counts()[index] > 0)
                .toArray();

        double lowestCostFound = Double.MAX_VALUE;
        int numberOfPossibleCombinations = 1 << distinctBooksPresent.length;
        for (int combination = 1; combination < numberOfPossibleCombinations; combination++) {
            Integer[] remainingBooks = currentBasket.counts().clone();
            int currentGroupSize = 0;
            for (int bookIndex = 0; bookIndex < distinctBooksPresent.length; bookIndex++) {
                boolean isBookInCurrentGroup = (combination & (1 << bookIndex)) != 0;
                if (isBookInCurrentGroup) {
                    int actualBookId = distinctBooksPresent[bookIndex];
                    remainingBooks[actualBookId]--;
                    currentGroupSize++;
                }
            }
            double groupPrice = BookDiscountDetails.GROUP_PRICE.get(currentGroupSize);
            double totalCostWithThisGrouping = groupPrice + calculateMinimumPrice(new BookBasket(remainingBooks));
            lowestCostFound = Math.min(lowestCostFound, totalCostWithThisGrouping);
        }
        basketCache.put(currentBasket, lowestCostFound);
        return lowestCostFound;
    }
}
