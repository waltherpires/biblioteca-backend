package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updateUser){
        return userRepository.findById(id)
            .map(user -> {
                user.setName(updateUser.getName());
                user.setEmail(updateUser.getEmail());
                return userRepository.save(user);
            }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void rentBook(Long userId, Long bookId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));

        if(book.getRenter() != null){
            throw new RuntimeException("Book is already rented");
        }

        book.setRenter(user);
        bookRepository.save(book);
    }
}
