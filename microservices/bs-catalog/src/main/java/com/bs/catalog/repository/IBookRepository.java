package com.bs.catalog.repository;

import java.util.List;
import java.util.UUID;
import com.bs.catalog.repository.model.Book;

public interface IBookRepository {

  List<Book> getAll();

  List<Book> search(String title, String author, Boolean active);

  Book getById(UUID id);

  Book getByIdAndActive(UUID id, Boolean active);

  Boolean existsByTitleAndAuthor(String title, String author);

  Boolean existsById(UUID id);

  Book save(Book book);

  void removeAll();

  void removeById(UUID id);
}
