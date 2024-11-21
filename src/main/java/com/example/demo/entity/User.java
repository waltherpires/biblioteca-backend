package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.observer.Observer;

import com.example.demo.entity.observer.Subject;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User implements Observer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    @ElementCollection
    private List<String> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "observers")
    private List<Subject> observedBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rent> rents = new ArrayList<>();

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
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

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<Subject> getObservedBooks() {
        return observedBooks;
    }

    public void setObservedBooks(List<Subject> observedBooks) {
        this.observedBooks = observedBooks;
    }

    //Notificar sobre livros disponíveis (Metodo relacionado ao Padrao Observer)
    @Override
    public void update(Book book) {
        String notification ="O livro: " + book.getTitle() + " está disponível!";
        getMessages().add(notification);
    }
}
