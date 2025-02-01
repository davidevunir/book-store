package com.bs.clients.repository.impl;

import static lombok.AccessLevel.PRIVATE;
import static com.bs.clients.utils.Constants.LAST_NAME;
import static com.bs.clients.utils.Constants.FIRST_NAME;
import static com.bs.clients.utils.SearchOperation.MATCH;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.clients.utils.SearchCriteria;
import com.bs.clients.utils.SearchStatement;
import com.bs.clients.repository.model.Client;
import org.springframework.stereotype.Repository;
import com.bs.clients.repository.IClientRepository;
import com.bs.clients.repository.ClientJpaRepository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientRepository implements IClientRepository {

  ClientJpaRepository repository;

  @Override
  public List<Client> getAll() {
    return repository.findAll();
  }

  @Override
  public List<Client> search(String firstName, String lastName) {
    SearchCriteria<Client> spec = new SearchCriteria<>();
    if (isNotBlank(firstName)) {
      spec.add(new SearchStatement(FIRST_NAME, firstName, MATCH));
    }
    if (isNotBlank(lastName)) {
      spec.add(new SearchStatement(LAST_NAME, lastName, MATCH));
    }

    return repository.findAll(spec);
  }

  @Override
  public Client getById(UUID id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  public Boolean existsById(UUID id) {
    return repository.existsById(id);
  }

  @Override
  public Client save(Client client) {
    return repository.save(client);
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