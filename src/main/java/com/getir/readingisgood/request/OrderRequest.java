package com.getir.readingisgood.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    @NotNull(message = "Customer ID cannot be null.")
    private Long customerId;

    @NotEmpty(message = "Book orders cannot be null or empty.")
    private List<BookOrderRequest> bookOrders;

    public OrderRequest() {
        bookOrders = new ArrayList<>();
    }

    public OrderRequest(Long customerId) {
        this.customerId = customerId;
        bookOrders = new ArrayList<>();
    }

    public OrderRequest(Long customerId, List<BookOrderRequest> bookOrders) {
        this.customerId = customerId;
        this.bookOrders = bookOrders;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<BookOrderRequest> getBookOrders() {
        return bookOrders;
    }

    public void setBookOrders(List<BookOrderRequest> bookOrders) {
        this.bookOrders = bookOrders;
    }
}
