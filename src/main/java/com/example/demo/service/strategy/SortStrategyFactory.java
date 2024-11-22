package com.example.demo.service.strategy;

import com.example.demo.entity.Book;
import org.springframework.stereotype.Service;

@Service
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

  public SortStrategy<Book> getStrategy(String sortBy){
      switch (sortBy.toLowerCase()) {
          case "title":
              return sortByTitleStrategy;
          case "title-desc":
              return sortByTitleDescendingStrategy;
          case "author":
              return sortByAuthorStrategy;
          default:
              throw new IllegalArgumentException("Strategy inválida: " + sortBy);
      }
  }

}
