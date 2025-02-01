package com.bs.catalog.controller;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.badRequest;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.catalog.service.IBookService;
import com.bs.catalog.repository.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookstore/v1/books")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookController {

  IBookService service;

  private static final String ID = "id";

  @GetMapping
  public ResponseEntity<List<Book>> getAll(@RequestParam(required = false) String title, @RequestParam(required = false) String author,
      @RequestParam(required = false) Boolean active) {
    return ok(service.getAll(title, author, active));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getById(@PathVariable(ID) UUID id, @RequestParam(required = false) Boolean active) {
    var response = service.getById(id, active);

    return response == null ? notFound().build() : ok(response);
  }

  @PostMapping
  public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
    var response = service.create(book);

    return response == null ? badRequest().build() : status(CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable(ID) UUID id, @Valid @RequestBody String item) {
    var response = service.update(id, item);

    return response == null ? notFound().build() : ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable(ID) UUID id, @Valid @RequestBody Book book) {
    var response = service.update(id, book);

    return response == null ? notFound().build() : ok(response);
  }

  @DeleteMapping
  public ResponseEntity<Void> removeAll() {
    service.removeAll();

    return ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeById(@PathVariable(ID) UUID id) {
    return TRUE.equals(service.removeById(id)) ? ok().build() : notFound().build();
  }
}
