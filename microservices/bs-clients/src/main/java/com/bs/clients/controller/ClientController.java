package com.bs.clients.controller;

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
import com.bs.clients.service.IClientService;
import com.bs.clients.repository.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore/v1/clients")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientController {

  IClientService service;

  private static final String ID = "id";

  @GetMapping
  public ResponseEntity<List<Client>> getAll(@RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false) String email,
      @RequestParam(required = false) Boolean active) {
    return ok(service.getAll(firstName, lastName, email, active));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> getById(@PathVariable(ID) UUID id) {
    var response = service.getById(id);

    return response == null ? notFound().build() : ok(response);
  }

  @PostMapping
  public ResponseEntity<Client> create(@Valid @RequestBody Client client) {
    var response = service.create(client);

    return response == null ? badRequest().build() : status(CREATED).body(response);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Client> update(@PathVariable(ID) UUID id, @Valid @RequestBody String item) {
    var response = service.update(id, item);

    return response == null ? notFound().build() : ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Client> update(@PathVariable(ID) UUID id, @Valid @RequestBody Client client) {
    var response = service.update(id, client);

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
