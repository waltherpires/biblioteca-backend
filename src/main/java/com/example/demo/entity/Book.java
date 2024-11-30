package com.example.demo.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.demo.entity.User.User;
import com.example.demo.entity.observer.Observer;
import com.example.demo.entity.observer.Subject;
import com.example.demo.enums.StatusBook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Book implements Subject{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private StatusBook status = StatusBook.DISPONIVEL; ;

    // Pessoas interessadas (Observers)
    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "book_observers",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> observers = new ArrayList<>();

    // Empréstimos
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rent> rents = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        if(!observers.contains(observer)) {
            observers.add((User) observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if(observers.contains(observer)){
            observers.remove((User) observer);
        }
    }

    @Override
    public void notifyObservers() {
        int position = 1;

        for (User user : observers){
            String message = "O livro '" + this.getTitle() + "' está disponível. Há " + (position - 1) + " pessoas a sua frente na fila.";
            user.getMessages().add(message);
            position++;
        }
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public StatusBook getStatus() {
        return status;
    }

    public void setStatus(StatusBook status) {
        this.status = status;
    }

    public List<User> getObservers() {
        return observers;
    }

    public void setObservers(List<User> observers) {
        this.observers = observers;
    }
}
