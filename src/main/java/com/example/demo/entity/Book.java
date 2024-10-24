package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.observer.Observer;
import com.example.demo.entity.observer.Subject;
import com.example.demo.enums.StatusBook;

import jakarta.persistence.*;

@Entity
public class Book implements Subject{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private StatusBook status;

    // Pessoas interessadas
    @ManyToMany
    @JoinTable(
        name = "book_observers",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> observers = new ArrayList<>();

    // Empréstimos
    @OneToMany(mappedBy = "book")
    private List<Rent> rents = new ArrayList<>();

    //Lista de espera
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaitlistEntry> waitlistEntries = new ArrayList<>();

    public List<WaitlistEntry> getWaitlistEntries() {
        return waitlistEntries;
    }

    public void setWaitlistEntries(List<WaitlistEntry> waitlistEntries) {
        this.waitlistEntries = waitlistEntries;
    }

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
        for (User user : observers){
            ((Observer) user).update(this);
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
