package com.getir.readingisgood.response;

public class BookOrderResponse {
    private final Long id;

    private final Long bookId;

    private final Long count;

    public BookOrderResponse(Long id, Long bookId, Long count) {
        this.id = id;
        this.bookId = bookId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getCount() {
        return count;
    }
}
