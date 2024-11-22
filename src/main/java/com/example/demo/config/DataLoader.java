package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {

        // Inserção de users
        String sqlUsers = "INSERT INTO users (name, email, phone) VALUES " +
                "('Walther', 'walther@hotmail.com', '123456789'), " +
                "('John Doe', 'john.doe@example.com', '987654321'), " +
                "('Jane Smith', 'jane.smith@example.com', '564738291'), " +
                "('Mark Taylor', 'mark.taylor@example.com', '238465748'), " +
                "('Lucy Brown', 'lucy.brown@example.com', '847263910'), " +
                "('Emily Johnson', 'emily.johnson@example.com', '675849302'), " +
                "('Michael White', 'michael.white@example.com', '129874563'), " +
                "('Sarah Lee', 'sarah.lee@example.com', '983475102'), " +
                "('David Clark', 'david.clark@example.com', '573829014'), " +
                "('Olivia Lewis', 'olivia.lewis@example.com', '390847562')";

        jdbcTemplate.execute(sqlUsers);

        // Inserção de 'books'
        String sqlBooks = "INSERT INTO book (title, author, status) VALUES " +
                "('Harry Potter', 'J. K. Rowling', 'DISPONIVEL'), " +
                "('The Lord of the Rings', 'J. R. R. Tolkien', 'DISPONIVEL'), " +
                "('Game of Thrones', 'George R. R. Martin', 'ALUGADO'), " +
                "('The Hobbit', 'J. R. R. Tolkien', 'RESERVADO'), " +
                "('Percy Jackson', 'Rick Riordan', 'DISPONIVEL'), " +
                "('The Catcher in the Rye', 'J. D. Salinger', 'ALUGADO'), " +
                "('1984', 'George Orwell', 'DISPONIVEL'), " +
                "('To Kill a Mockingbird', 'Harper Lee', 'RESERVADO'), " +
                "('Pride and Prejudice', 'Jane Austen', 'DISPONIVEL'), " +
                "('The Great Gatsby', 'F. Scott Fitzgerald', 'ALUGADO')";

        jdbcTemplate.execute(sqlBooks);
    }
}
