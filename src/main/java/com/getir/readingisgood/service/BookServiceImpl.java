package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.exception.EntityAlreadyExistsException;
import com.getir.readingisgood.exception.EntityNotFoundException;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.request.AddBookRequest;
import com.getir.readingisgood.request.UpdateBookStockRequest;
import com.getir.readingisgood.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse addBook(AddBookRequest addBookRequest) {
        if (bookRepository.existsByIsbn(addBookRequest.getIsbn())) {
            throw new EntityAlreadyExistsException("Book with ISBN " + addBookRequest.getIsbn() + " already exists.");
        }
        Book book = bookRepository.save(
                new Book(addBookRequest.getIsbn(), addBookRequest.getTitle(),
                        addBookRequest.getAuthor(), addBookRequest.getPrice(), addBookRequest.getStock()));
        return new BookResponse(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(),
                book.getPrice(), book.getStock());
    }

    public BookResponse updateBookStock(UpdateBookStockRequest updateBookStockRequest) {
        Book book = bookRepository.findById(updateBookStockRequest.getId()).orElseThrow(() ->
                new EntityNotFoundException("Book with ID " + updateBookStockRequest.getId() + " does not exist."));
        book.setStock(updateBookStockRequest.getStock());
        bookRepository.save(book);
        return new BookResponse(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(),
                book.getPrice(), book.getStock());
    }
}
