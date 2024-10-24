package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.observer.Observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private List<Book> observedBooks = new ArrayList<>();

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
        System.out.println(name + " Notificado");
    }
}
