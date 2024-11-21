package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.service.strategy.SortStrategy;

public class BookSortService {

  private SortStrategy<Book> sortStrategy;

  public void setSortStrategy(SortStrategy<Book> sortStrategy){
    this.sortStrategy = sortStrategy;
  }

  public List<Book> sortBooks(List<Book> books){
    return sortStrategy.sort(books);
  }

}
