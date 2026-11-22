package com.example.tomcat.web;

import com.example.tomcat.entity.Book;
import com.example.tomcat.service.BookService;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> listBook() {
        return bookService.list();
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationError(ConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_XML)
                .body("<error>" + ex.getMessage() + "</error>");
    }
}