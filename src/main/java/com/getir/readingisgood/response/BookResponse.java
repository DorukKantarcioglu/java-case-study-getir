package com.getir.readingisgood.response;

public class BookResponse {
    private final Long id;

    private final String isbn;

    private final String title;

    private final String author;

    private final Double price;

    private final Long stock;

    public BookResponse(Long id, String isbn, String title, String author, Double price, Long stock) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

    public Long getStock() {
        return stock;
    }
}
