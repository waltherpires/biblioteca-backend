package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.observer.Observer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User implements Observer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    @ManyToMany(mappedBy = "observers")
    private List<Book> observedBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rent> rents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaitlistEntry> waitlistEntries = new ArrayList<>();

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }



    public List<WaitlistEntry> getWaitlistEntries() {
        return waitlistEntries;
    }

    public void setWaitlistEntries(List<WaitlistEntry> waitlistEntries) {
        this.waitlistEntries = waitlistEntries;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getObservedBooks() {
        return observedBooks;
    }

    public void setObservedBooks(List<Book> observedBooks) {
        this.observedBooks = observedBooks;
    }

    @Override
    public void update(Book book) {
        
    }
}
