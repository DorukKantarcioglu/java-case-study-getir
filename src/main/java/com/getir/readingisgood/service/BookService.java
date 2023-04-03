package com.getir.readingisgood.service;

import com.getir.readingisgood.request.AddBookRequest;
import com.getir.readingisgood.request.UpdateBookStockRequest;
import com.getir.readingisgood.response.BookResponse;

public interface BookService {
    BookResponse addBook(AddBookRequest addBookRequest);

    BookResponse updateBookStock(UpdateBookStockRequest updateBookStockRequest);
}
