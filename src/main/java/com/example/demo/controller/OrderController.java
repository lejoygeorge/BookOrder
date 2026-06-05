package com.example.demo.controller;

import com.example.demo.model.BookItem;
import com.example.demo.model.BillDetails;
import com.example.demo.service.PriceCalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PriceCalculatorService service;

    public OrderController(PriceCalculatorService service) {
        this.service = service;
    }

    @GetMapping("/book")
    public BillDetails getOrder(@RequestBody List<BookItem> request) {
        return service.calculatePrice(request);
    }
}