package com.bs.clients.service;

import java.util.List;
import java.util.UUID;
import com.bs.clients.repository.model.Client;

public interface IClientService {

  List<Client> getAll(String firstName, String lastName, String email, Boolean active);

  Client getById(UUID id);

  Client create(Client client);

  void removeAll();

  Boolean removeById(UUID id);

  Client update(UUID id, String item);

  Client update(UUID id, Client client);
}