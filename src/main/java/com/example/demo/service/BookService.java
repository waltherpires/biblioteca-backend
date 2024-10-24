package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

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

    public Book addObserverToBookList(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
     
        // metodo observer
        book.registerObserver(user);

        if(!user.getObservedBooks().contains(book)){
            user.getObservedBooks().add(book);
        }

        userRepository.save(user);
        return bookRepository.save(book);
    }

}
