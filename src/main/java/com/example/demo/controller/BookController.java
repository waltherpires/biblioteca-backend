package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Rent;
import com.example.demo.entity.User;
import com.example.demo.service.RentService;
import com.example.demo.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

@Controller
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;
    private RentService rentService;
    private UserService userService;

    public BookController(BookService bookService, RentService rentService, UserService userService){
        this.bookService = bookService;
        this.rentService = rentService;
        this.userService = userService;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return bookService.findBookById(id)
            .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping 
    public Book createBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        try{
            return new ResponseEntity<>(bookService.updateBook(id, book), HttpStatus.OK);
        } catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{bookId}/rents/{userId}")
    public ResponseEntity<Rent> createRent(@PathVariable Long bookId, @PathVariable Long userId){
        Book book = bookService.findBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        try{
            Rent createdRent = rentService.createRent(user, book);
            return new ResponseEntity<>(createdRent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<> (HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/rents/{rentId}")
    public ResponseEntity<Void> finishRent(@PathVariable Long bookId, @PathVariable Long rentId){
        Rent rent = rentService.findRentById(rentId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));

        try {
            rentService.returnBook(rent);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch( RuntimeException e ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
