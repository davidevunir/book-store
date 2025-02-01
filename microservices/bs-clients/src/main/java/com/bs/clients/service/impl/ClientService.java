package com.bs.clients.service.impl;

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
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.bs.clients.service.IClientService;
import com.bs.clients.repository.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bs.clients.repository.IClientRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientService implements IClientService {

  ObjectMapper objectMapper;
  IClientRepository repository;

  private boolean exists(String email) {
    if (TRUE.equals(repository.existsByEmail(email))) {
      log.error("El cliente con email: {}, ya existe en el sistema", email);

      return true;
    }

    return false;
  }

  @Override
  public List<Client> getAll(String firstName, String lastName) {
    if (hasLength(firstName) || hasLength(lastName)) {
      return repository.search(firstName, lastName);
    }

    return repository.getAll();
  }

  @Override
  public Client getById(UUID id) {
    if (id == null) {
      return null;
    }

    return repository.getById(id);
  }

  @Override
  public Client create(Client client) {
    if (client == null || this.exists(client.getEmail())) {
      return null;
    }

    return repository.save(client);
  }

  @Override
  public Client update(UUID id, String item) {
    if (id == null || isBlank(item)) {
      return null;
    }

    var currentBook = repository.getById(id);
    if (currentBook == null) {
      return null;
    }

    try {
      var target = fromJson(objectMapper.readTree(item)).apply(objectMapper.readTree(objectMapper.writeValueAsString(currentBook)));
      return repository.save(objectMapper.treeToValue(target, Client.class));
    } catch (JsonProcessingException | JsonPatchException e) {
      log.error("Error al actualizar el cliente con id: {} - {}", id, e.getMessage());
      return null;
    }
  }

  @Override
  public Client update(UUID id, Client updatedClient) {
    if (id == null || updatedClient == null || this.exists(updatedClient.getEmail())) {
      return null;
    }

    var currentClient = repository.getById(id);
    if (currentClient == null) {
      return null;
    }

    currentClient.update(updatedClient);
    return repository.save(currentClient);
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
}

