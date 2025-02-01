package com.bs.catalog.repository.impl;

import static lombok.AccessLevel.PRIVATE;
import static com.bs.catalog.utils.Constants.TITLE;
import static com.bs.catalog.utils.Constants.AUTHOR;
import static com.bs.catalog.utils.Constants.ACTIVE;
import static com.bs.catalog.utils.SearchOperation.MATCH;
import static com.bs.catalog.utils.SearchOperation.EQUAL;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.catalog.utils.SearchCriteria;
import com.bs.catalog.utils.SearchStatement;
import com.bs.catalog.repository.model.Book;
import org.springframework.stereotype.Repository;
import com.bs.catalog.repository.IBookRepository;
import com.bs.catalog.repository.BookJpaRepository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookRepository implements IBookRepository {

  BookJpaRepository repository;

  @Override
  public List<Book> getAll() {
    return repository.findAll();
  }

  @Override
  public List<Book> search(String title, String author, Boolean active) {
    SearchCriteria<Book> spec = new SearchCriteria<>();
    if (isNotBlank(title)) {
      spec.add(new SearchStatement(TITLE, title, MATCH));
    }
    if (isNotBlank(author)) {
      spec.add(new SearchStatement(AUTHOR, author, MATCH));
    }
    if (active != null) {
      spec.add(new SearchStatement(ACTIVE, active, EQUAL));
    }

    return repository.findAll(spec);
  }

  @Override
  public Book getById(UUID id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public Book getByIdAndActive(UUID id, Boolean active) {
    return repository.findByIdAndActive(id, active);
  }

  @Override
  public Boolean existsByTitleAndAuthor(String title, String author) {
    return repository.existsByTitleAndAuthor(title, author);
  }

  @Override
  public Boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  @Override
  public Book save(Book book) {
    return repository.save(book);
  }

  @Override
  public void removeAll() {
    repository.deleteAll();
  }

  @Override
  public void removeById(UUID id) {
    repository.deleteById(id);
  }
}
