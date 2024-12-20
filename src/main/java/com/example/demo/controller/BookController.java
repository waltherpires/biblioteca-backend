package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.Rent;
import com.example.demo.entity.User.User;
import com.example.demo.service.BookSortService;
import com.example.demo.service.RentService;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final RentService rentService;
    private final UserService userService;
    private final BookSortService bookSortService;

    public BookController(
            BookService bookService,
            RentService rentService,
            UserService userService,
            BookSortService bookSortService
    ){
        this.bookService = bookService;
        this.rentService = rentService;
        this.userService = userService;
        this.bookSortService = bookSortService;
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Book>> getSortedBooks(@RequestParam String sortBy){
        List<Book> books = bookService.findAllBooks();
        List<Book> sortedBooks = bookSortService.sortBooks(books, sortBy);
        return ResponseEntity.ok(sortedBooks);
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

    // Criar reserva
    @PostMapping("/{bookId}/rents/{userId}/reserve")
    public ResponseEntity<Rent> createReserve(@PathVariable Long bookId, @PathVariable Long userId){
        Book book = bookService.findBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        try{
            Rent createdRent = rentService.createReserve(user, book);
            return new ResponseEntity<>(createdRent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<> (HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{bookId}/rents")
    public ResponseEntity<List<Rent>> getRentsByBookId(@PathVariable Long bookId) {
        List<Rent> rents = rentService.findRentsByBookId(bookId);
        return ResponseEntity.ok(rents);
    }

    // Confirmar reserva e transformar em aluguel
    @PostMapping("/{bookId}/rents/{rentId}/confirm")
    public ResponseEntity<String> confirmRent(@PathVariable Long rentId) {
        try {
            rentService.confirmRent(rentId); // Confirma o aluguel
            return new ResponseEntity<>("Aluguel confirmado com sucesso.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erro ao confirmar o aluguel", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{bookId}/rents/{rentId}")
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
