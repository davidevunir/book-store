package com.bs.catalog.service;

import java.util.List;
import java.util.UUID;
import com.bs.catalog.repository.model.Book;

public interface IBookService {

  List<Book> getAll(String title, String author, Boolean active);

  Book getById(UUID id, Boolean active);

  Book create(Book book);

  void removeAll();

  Boolean removeById(UUID id);

  Book update(UUID id, String item);

  Book update(UUID id, Book book);
}
