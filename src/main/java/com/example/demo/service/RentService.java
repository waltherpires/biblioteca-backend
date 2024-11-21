package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import com.example.demo.enums.StatusBook;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.User;
import com.example.demo.repository.RentRepository;

@Service
public class RentService {
    
    private final RentRepository rentRepository;
    private final BookService bookService;
    private final UserService userService;

    public RentService(RentRepository rentRepository, BookService bookService, UserService userService) {
        this.rentRepository = rentRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    public List<Rent> findAllRents(){
        return rentRepository.findAll();
    }

    public Optional<Rent> findRentById(Long id){
        return rentRepository.findById(id);
    }

    public Rent updateRent(Long id, Rent updatedRent){
        return rentRepository.findById(id)
            .map(rent -> {
                rent.setReturnDate(updatedRent.getReturnDate());
                rent.setBook(updatedRent.getBook());
                rent.setUser(updatedRent.getUser());
                rent.setDueDate(updatedRent.getDueDate());
                rent.setRentDate(updatedRent.getRentDate());
                return rentRepository.save(rent);
            }).orElseThrow(() -> new RuntimeException("Cannot update Rent"));
    }
    
    public Rent saveRent(Rent rent){
        return rentRepository.save(rent);
    }

    public Rent createRent(User user, Book book){
        Rent rent = new Rent();

        book.registerObserver(user);

        rent.getBook().setStatus(StatusBook.RESERVADO);
        rent.setUser(user);
        rent.setBook(book);
        rent.setReturned(false);

        LocalDate rentDate = LocalDate.now();
        rent.setRentDate(rentDate);

        rent.setDueDate(rentDate.plusDays(14));

        return rentRepository.save(rent);
    }

    public void returnBook(Rent rent){
        rent.setReturned(true);
        rent.setReturnDate(LocalDate.now());

        //Removendo user de observers do book
        User user = rent.getUser();
        Book book = rent.getBook();
        bookService.removeObserverFromBook(book.getId(), user.getId());

        book.setStatus(StatusBook.DISPONIVEL);
    }    
}
