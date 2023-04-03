package com.getir.readingisgood.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BookOrderRequest {
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;

    @NotNull(message = "Book count cannot be null.")
    @Positive(message = "Book count need to be greater than zero.")
    private Long bookCount;

    public BookOrderRequest() {}

    public BookOrderRequest(Long bookId, Long bookCount) {
        this.bookId = bookId;
        this.bookCount = bookCount;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookCount() {
        return bookCount;
    }

    public void setBookCount(Long bookCount) {
        this.bookCount = bookCount;
    }
}
