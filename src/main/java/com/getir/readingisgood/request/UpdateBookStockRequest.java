package com.getir.readingisgood.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class UpdateBookStockRequest {
    @NotNull(message = "ID cannot be null.")
    public Long id;

    @NotNull(message = "Stock cannot be null.")
    @PositiveOrZero(message = "Stock cannot be negative.")
    public Long stock;

    public UpdateBookStockRequest() {}

    public UpdateBookStockRequest(Long id, Long stock) {
        this.id = id;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
