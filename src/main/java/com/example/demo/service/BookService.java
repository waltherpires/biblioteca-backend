package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.User;
import com.example.demo.entity.WaitlistEntry;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RentService rentService;

    public BookService(BookRepository bookRepository, UserRepository userRepository, RentService rentService){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

        this.rentService = rentService;
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

    public void addObserverToBook(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
     
        // metodo observer
        book.registerObserver(user);
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
 
    //Adicionar usuario a fila de espera
    public void addToWaitList(Book book, User user){
        WaitlistEntry entry = new WaitlistEntry();
        entry.setBook(book);
        entry.setUser(user);
        entry.setAddedDate(LocalDate.now());

        book.getWaitlistEntries().add(entry);

        // Adicionar usuário como observador
        if (!book.getObservers().contains(user)) {
            book.getObservers().add(user);
        }
    }

    //Alugar livro para próximo da fila
    public void rentToNextUser(Book book){
        if(!book.getWaitlistEntries().isEmpty()){
            
            //remover primeiro da fila de espera
            WaitlistEntry entry = book.getWaitlistEntries().remove(0);
            User nextUser = entry.getUser();

            // criar novo emprestimo para o primeiro da fila
            Rent newRent = rentService.createRent(nextUser, book);
            book.getRents().add(newRent);

            //remover usuário da lista de observers
            if(book.getObservers().contains(nextUser)){
                book.removeObserver(nextUser);
            }
        }
    }
}
