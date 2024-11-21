package com.example.demo.service.strategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Book;

public class SortByTitleDescendingStrategy implements SortStrategy<Book>{

  @Override
  public List<Book> sort(List<Book> items) {
    return items.stream()
      .sorted(Comparator.comparing(Book::getTitle).reversed())
      .collect(Collectors.toList());
  }
}
