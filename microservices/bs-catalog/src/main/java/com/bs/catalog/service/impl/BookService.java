package com.bs.catalog.service.impl;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.springframework.util.StringUtils.hasLength;
import static com.github.fge.jsonpatch.mergepatch.JsonMergePatch.fromJson;

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.catalog.service.IBookService;
import com.bs.catalog.repository.model.Book;
import org.springframework.stereotype.Service;
import com.bs.catalog.repository.IBookRepository;
import com.github.fge.jsonpatch.JsonPatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookService implements IBookService {

  ObjectMapper objectMapper;
  IBookRepository repository;

  private boolean exists(String title, String author) {
    if (TRUE.equals(repository.existsByTitleAndAuthor(title, author))) {
      log.error("El libro: {}, del autor: {}, ya existe en el sistema", title, author);

      return true;
    }

    return false;
  }

  @Override
  public List<Book> getAll(String title, String author, Boolean active) {
    if (hasLength(title) || hasLength(author) || active != null) {
      return repository.search(title, author, active);
    }

    return repository.getAll();
  }

  @Override
  public Book getById(UUID id, Boolean active) {
    if (id == null) {
      return null;
    }

    if (active != null) {
      return repository.getByIdAndActive(id, active);
    }

    return repository.getById(id);
  }

  @Override
  public Book create(Book book) {
    if (book == null || this.exists(book.getTitle(), book.getAuthor())) {
      return null;
    }

    return repository.save(book);
  }

  @Override
  public void removeAll() {
    repository.removeAll();
  }

  @Override
  public Boolean removeById(UUID id) {
    if (TRUE.equals(repository.existsById(id))) {
      repository.removeById(id);

      return true;
    }

    return false;
  }

  @Override
  public Book update(UUID id, String item) {
    if (id == null || isBlank(item)) {
      return null;
    }

    var currentBook = repository.getById(id);
    if (currentBook == null) {
      return null;
    }

    try {
      var target = fromJson(objectMapper.readTree(item)).apply(objectMapper.readTree(objectMapper.writeValueAsString(currentBook)));
      return repository.save(objectMapper.treeToValue(target, Book.class));
    } catch (JsonProcessingException | JsonPatchException e) {
      log.error("Error al actualizar el libro con id: {} - {}", id, e.getMessage());
      return null;
    }
  }

  @Override
  public Book update(UUID id, Book updatedBook) {
    if (id == null || updatedBook == null || this.exists(updatedBook.getTitle(), updatedBook.getAuthor())) {
      return null;
    }

    var currentBook = repository.getById(id);
    if (currentBook == null) {
      return null;
    }

    currentBook.update(updatedBook);
    return repository.save(currentBook);
  }
}
