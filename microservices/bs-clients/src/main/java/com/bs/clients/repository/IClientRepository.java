package com.bs.clients.repository;

import java.util.List;
import java.util.UUID;
import com.bs.clients.repository.model.Client;

public interface IClientRepository {

  List<Client> getAll();

  List<Client> search(String firstName, String lastName);

  Client getById(UUID id);

  Boolean existsByEmail(String email);

  Boolean existsById(UUID id);

  Client save(Client Client);

  void removeAll();

  void removeById(UUID id);
}

