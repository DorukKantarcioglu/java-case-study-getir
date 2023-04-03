package com.getir.readingisgood.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class AddBookRequest {
    @NotBlank(message = "ISBN cannot be null or blank.")
    private String isbn;

    @NotBlank(message = "Title cannot be null or blank.")
    private String title;

    @NotBlank(message = "Author cannot be null or blank.")
    private String author;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price has to be positive.")
    private Double price;

    @PositiveOrZero(message = "Stock cannot be negative.")
    private Long stock;

    public AddBookRequest() {
        stock = 0L;
    }

    public AddBookRequest(String isbn, String title, String author, Double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        stock = 0L;
    }

    public AddBookRequest(String isbn, String title, String author, Double price, Long stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
