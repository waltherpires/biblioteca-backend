package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import java.time.LocalDate;

import com.example.demo.enums.StatusBook;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.User.User;
import com.example.demo.repository.RentRepository;

@Service
public class RentService {
    
    private final RentRepository rentRepository;
    private final BookService bookService;

    public RentService(RentRepository rentRepository, BookService bookService) {
        this.rentRepository = rentRepository;
        this.bookService = bookService;
    }

    public List<Rent> findAllRents(){
        return rentRepository.findAll();
    }

    public Optional<Rent> findRentById(Long id){
        return rentRepository.findById(id);
    }

    public List<Rent> findRentsByBookId(Long bookId) {
        return rentRepository.findByBookId(bookId);
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

    public Rent createReserve(User user, Book book) {
        boolean userAlreadyInQueue = book.getRents().stream()
                .anyMatch(rent -> rent.getUser().equals(user) && !rent.isReturned());

        if (userAlreadyInQueue) {
            throw new IllegalArgumentException("Usuário já está na fila de reservas para este livro.");
        }

        Rent rent = new Rent();

        // Adiciona o usuário como observador do livro
        bookService.addObserverToBook(book, user);

        rent.setUser(user);
        rent.setBook(book);
        rent.setReturned(false); // A reserva não foi devolvida

        // Não define rentDate nem dueDate ainda
        rent.setRentDate(null); // Rent ainda não começou
        rent.setDueDate(null);  // Não tem data de devolução

        // Salva a reserva
        return rentRepository.save(rent);
    }

    public void confirmRent(Long rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        // Verifica se a data de aluguel ainda não foi definida
        if (rent.getRentDate() == null) {
            LocalDate rentDate = LocalDate.now();
            rent.setRentDate(rentDate); // Define rentDate como a data atual
            rent.setDueDate(rentDate.plusDays(14)); // Define a dueDate (prazo de devolução)
            rentRepository.save(rent); // Atualiza no banco
        }
    }

    public void returnBook(Rent rent){
        rent.setReturned(true);
        rent.setReturnDate(LocalDate.now());

        //Removendo user de observers do book
        User user = rent.getUser();
        Book book = rent.getBook();
        bookService.removeObserverFromBook(book.getId(), user.getId());

        book.setStatus(StatusBook.DISPONIVEL);

        book.notifyObservers();
    }    
}
