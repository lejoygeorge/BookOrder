package com.bookorder.demo.controller;

import com.bookorder.demo.model.BillDetails;
import com.bookorder.demo.model.BookItem;
import com.bookorder.demo.model.BooksEnum;
import com.bookorder.demo.service.PriceCalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PriceCalculatorService priceCalculatorService;

    @Test
    @DisplayName("Positive: Should return HTTP 200 and calculated bill details for a valid request")
    void testGetOrder_Success() throws Exception {
        // Arrange: Create fake request data and mock the service response
        List<BookItem> requestItems = List.of(
                new BookItem(BooksEnum.BOOK1, 2),
                new BookItem(BooksEnum.BOOK2, 1)
        );
        BillDetails mockReceipt = new BillDetails();
        mockReceipt.setOriginalPrice(150.0);
        mockReceipt.setFinalPrice(145.0);
        mockReceipt.setDiscount(5.0);
        when(priceCalculatorService.calculatePrice(anyList())).thenReturn(mockReceipt);
        mockMvc.perform(get("/api/orders/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestItems)))
                .andExpect(status().isOk()) // Expect HTTP 200
                .andExpect(jsonPath("$.originalPrice").value(150.0))
                .andExpect(jsonPath("$.finalPrice").value(145.0))
                .andExpect(jsonPath("$.discount").value(5.0));
        verify(priceCalculatorService).calculatePrice(anyList());
    }

    @Test
    @DisplayName("Negative: Should return HTTP 400 Bad Request when request body is completely missing")
    void testGetOrder_MissingBody_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/orders/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(priceCalculatorService);
    }
}