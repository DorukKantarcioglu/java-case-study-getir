package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.request.AddBookRequest;
import com.getir.readingisgood.request.UpdateBookStockRequest;
import com.getir.readingisgood.response.BookResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(BookService.class);
    }

    @Test
    void testAddBook() {
        AddBookRequest addBookRequest = new AddBookRequest("isbn", "title", "author", 15.0);
        Book book = new Book("isbn", "title", "author", 15.0);
        book.setId(1L);

        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookResponse bookResponse = bookService.addBook(addBookRequest);

        Assertions.assertNotNull(bookResponse);
        Assertions.assertEquals(1, bookResponse.getId());
        Assertions.assertEquals("isbn", bookResponse.getIsbn());
        Assertions.assertEquals("title", bookResponse.getTitle());
        Assertions.assertEquals("author", bookResponse.getAuthor());
        Assertions.assertEquals(15.0, bookResponse.getPrice());
        Assertions.assertEquals(0, bookResponse.getStock());
    }

    @Test
    void testUpdateBookStock() {
        UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest(1L, 25L);
        Book book = new Book("isbn", "title", "author", 15.0);
        book.setId(1L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        book.setStock(25L);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookResponse bookResponse = bookService.updateBookStock(updateBookStockRequest);

        Assertions.assertNotNull(bookResponse);
        Assertions.assertEquals(1, bookResponse.getId());
        Assertions.assertEquals("isbn", bookResponse.getIsbn());
        Assertions.assertEquals("title", bookResponse.getTitle());
        Assertions.assertEquals("author", bookResponse.getAuthor());
        Assertions.assertEquals(15.0, bookResponse.getPrice());
        Assertions.assertEquals(25, bookResponse.getStock());
    }
}
