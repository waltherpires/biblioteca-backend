package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.service.strategy.SortStrategy;
import com.example.demo.service.strategy.SortStrategyFactory;
import org.springframework.stereotype.Service;

@Service
public class BookSortService {

  private SortStrategyFactory sortStrategyFactory;

  public BookSortService(SortStrategyFactory sortStrategyFactory){
    this.sortStrategyFactory = sortStrategyFactory;
  }

  public List<Book> sortBooks(List<Book> books, String sortBy){
    SortStrategy<Book> strategy = sortStrategyFactory.getStrategy(sortBy);
    return strategy.sort(books);
  }
}
