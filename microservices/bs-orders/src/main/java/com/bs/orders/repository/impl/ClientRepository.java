package com.bs.orders.repository.impl;

import static lombok.AccessLevel.PRIVATE;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.NonFinal;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.repository.dto.Client;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientRepository {

  @NonFinal
  @Value("${service.clients.url}")
  String clientsUrl;

  RestTemplate restTemplate;

  public Client getClientById(UUID id) {
    try {
      return restTemplate.getForObject(String.format(clientsUrl, "bookstore/v1/clients/" + id), Client.class);
    } catch (HttpClientErrorException e) {
      log.error("No se encontró un cliente con id: {} - Client Error: {}", id, e.getStatusCode());
      return null;
    } catch (HttpServerErrorException e) {
      log.error("No se encontró un cliente con id: {} - Server Error: {}", id, e.getStatusCode());
      return null;
    } catch (Exception e) {
      log.error("Error Interno con el cliente: {} - {}", id, e.getMessage());
      return null;
    }
  }
}