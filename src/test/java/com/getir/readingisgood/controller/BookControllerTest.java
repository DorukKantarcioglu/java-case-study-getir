package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.request.AddBookRequest;
import com.getir.readingisgood.request.UpdateBookStockRequest;
import com.getir.readingisgood.response.BookResponse;
import com.getir.readingisgood.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = BookController.class)
public class BookControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BookService bookService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(bookService);
    }

    @Test
    void testAddBook() throws Exception {
        AddBookRequest addBookRequest = new AddBookRequest("isbn", "title", "author", 15.0);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(addBookRequest);

        BookResponse bookResponse = new BookResponse(1L, "isbn", "title", "author", 15.0, 0L);
        Mockito.when(bookService.addBook(Mockito.any(AddBookRequest.class))).thenReturn(bookResponse);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/books").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        Mockito.verify(bookService).addBook(Mockito.any(AddBookRequest.class));
    }

    @Test
    void testUpdateBookStock() throws Exception {
        UpdateBookStockRequest updateBookStockRequest = new UpdateBookStockRequest(1L, 15L);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateBookStockRequest);

        BookResponse bookResponse = new BookResponse(1L, "isbn", "title", "author", 15.0, 15L);
        Mockito.when(bookService.updateBookStock(Mockito.any(UpdateBookStockRequest.class))).thenReturn(bookResponse);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/books").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Mockito.verify(bookService).updateBookStock(Mockito.any(UpdateBookStockRequest.class));
    }
}
