package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.User;
import com.example.demo.repository.RentRepository;

@Service
public class RentService {
    
    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
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
        rent.setUser(user);
        rent.setBook(book);

        LocalDate rentDate = LocalDate.now();
        rent.setRentDate(rentDate);

        rent.setDueDate(rentDate.plusDays(14));

        return rentRepository.save(rent);
    }


    public void ReturnBook(Rent rent){
        rent.setReturned(true);
        rent.setReturnDate(LocalDate.now());

        Book book = rent.getBook();
        book.notifyObservers();
    }
    
}
