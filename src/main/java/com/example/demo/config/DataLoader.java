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
        String sqlUsers = "INSERT INTO users (name, email, password, phone, type_of_user) VALUES" +
                "('Walther', 'walther@hotmail.com', 'senhaWalther', '123456789', 'usuario')," +
                "('John Doe', 'john.doe@example.com', 'senhaJohn', '987654321', 'professor')," +
                "('Jane Smith', 'jane.smith@example.com', 'senhaJane', '564738291', 'administrador')," +
                "('Mark Taylor', 'mark.taylor@example.com', 'senhaMark', '238465748', 'usuario')," +
                "('Lucy Brown', 'lucy.brown@example.com', 'senhaLucy', '847263910', 'professor')," +
                "('Emily Johnson', 'emily.johnson@example.com', 'senhaEmily', '675849302', 'usuario')," +
                "('Michael White', 'michael.white@example.com', 'senhaMichael', '129874563', 'usuario')," +
                "('Sarah Lee', 'sarah.lee@example.com', 'senhaSarah', '983475102', 'administrador')," +
                "('David Clark', 'david.clark@example.com', 'senhaDavid', '573829014', 'usuario')," +
                "('Olivia Lewis', 'olivia.lewis@example.com', 'senhaOlivia', '390847562', 'professor');";

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
