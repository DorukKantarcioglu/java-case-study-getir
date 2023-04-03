package com.getir.readingisgood.controller;

import com.getir.readingisgood.request.AddBookRequest;
import com.getir.readingisgood.request.UpdateBookStockRequest;
import com.getir.readingisgood.response.BookResponse;
import com.getir.readingisgood.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        return new ResponseEntity<>(bookService.addBook(addBookRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookResponse> updateBookStock(@Valid @RequestBody UpdateBookStockRequest updateBookStockRequest) {
        return new ResponseEntity<>(bookService.updateBookStock(updateBookStockRequest), HttpStatus.OK);
    }
}
