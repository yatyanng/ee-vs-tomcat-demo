package com.example.tomcat.rest;

import com.example.tomcat.entity.Book;
import com.example.tomcat.service.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@ApplicationPath("/api")
@Path("/books")
public class BookResource extends Application {

    @Inject
    private BookService bookService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_XML)
    public Response addBook(Book book) throws Exception {
        Book saved = bookService.create(book);
        return Response.ok(bookService.toXml(saved)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
        return bookService.findAll();
    }
}