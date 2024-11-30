package com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.entity.Rent;
import com.example.demo.entity.observer.Observer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements Observer, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "typeOfUser")
    @Enumerated(EnumType.STRING)
    private UserRoles typeOfUser;

    @ElementCollection
    private List<String> messages = new ArrayList<>();

    @ManyToMany(mappedBy = "observers")
    private List<Book> observedBooks = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
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

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public UserRoles getTypeOfUser() { return typeOfUser; }

    public void setTypeOfUser(UserRoles typeOfUser) { this.typeOfUser = typeOfUser; }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<Book> getObservedBooks() {
        return observedBooks;
    }


    public void setObservedBooks(List<Book> observedBooks) {
        this.observedBooks = observedBooks;
    }

    //Notificar sobre livros disponíveis (Metodo relacionado ao Padrao Observer)
    @Override
    public void update(Book book) {
        String notification ="O livro: " + book.getTitle() + " está disponível!";
        getMessages().add(notification);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(typeOfUser);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
