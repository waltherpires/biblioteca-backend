package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Rent;
import com.example.demo.repository.RentRepository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // CRUD Livros
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> findBookById(Long id){
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updateBook){
        return bookRepository.findById(id)
            .map(book -> {
                book.setTitle(updateBook.getTitle());
                book.setAuthor(updateBook.getAuthor());
                book.setStatus(updateBook.getStatus());
                return bookRepository.save(book);
            }).orElseThrow(() -> new RuntimeException("Book not found!"));
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    // Metodos relacionados a Observer
    public Book addObserverToBookById(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        addObserverToBook(book, user);
        return book;
    }

    public void addObserverToBook(Book book, User user){
        book.registerObserver(user);
        bookRepository.save(book);

        if(!user.getObservedBooks().contains(book)){
            user.getObservedBooks().add(book);
        }

        userRepository.save(user);
    }

    public void checkAndNotifyNextRent(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        Rent firstRent = book.getRents().getFirst();

        Rent nextRent = book.getRents().get(1);
        if (nextRent != null) {
            User nextUser = nextRent.getUser();
            book.notifyObservers();
        }
    }

    public void notifyObservers(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        //metodo observer
        book.notifyObservers();
    }

    public void removeObserverFromBook(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
     
        // metodo observer
        book.removeObserver(user);

        if(user.getObservedBooks().contains(book)){
            user.getObservedBooks().remove(book);
        }

        userRepository.save(user);
        bookRepository.save(book);
    }

}
