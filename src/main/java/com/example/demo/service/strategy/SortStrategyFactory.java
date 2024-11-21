package com.example.demo.service.strategy;

import com.example.demo.entity.Book;

public class SortStrategyFactory {

  private final SortByTitleStrategy sortByTitleStrategy;
  private final SortByTitleDescendingStrategy sortByTitleDescendingStrategy;
  private final SortByAuthorStrategy sortByAuthorStrategy;

  public SortStrategyFactory(
    SortByTitleStrategy sortByTitleStrategy, 
    SortByTitleDescendingStrategy sortByTitleDescendingStrategy, 
    SortByAuthorStrategy sortByAuthorStrategy){

      this.sortByTitleStrategy = sortByTitleStrategy;
      this.sortByTitleDescendingStrategy = sortByTitleDescendingStrategy;
      this.sortByAuthorStrategy = sortByAuthorStrategy;
  }

  public SortStrategy<Book> geStrategy(String sortBy){
    switch (sortBy)
  }

}
