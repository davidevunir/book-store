package com.bs.orders.repository.impl;

import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.bs.orders.repository.dto.Book;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookRepository {

  @Value("${service.catalog.url}")
  private String catalogUrl;

  private final RestTemplate restTemplate;

  public Book getBookByIdAndActive(UUID id, Boolean active) {
    try {
      var builder = fromUriString(String.format(catalogUrl, "bookstore/v1/books/" + id));
      if (active != null) {
        builder.queryParam("active", active);
      }

      return restTemplate.getForObject(builder.toUriString(), Book.class);
    } catch (HttpClientErrorException e) {
      log.error("No se encontró un libro con id: {} - Client Error: {}", id, e.getStatusCode());
      return null;
    } catch (HttpServerErrorException e) {
      log.error("No se encontró un libro con id: {} - Server Error: {}", id, e.getStatusCode());
      return null;
    } catch (Exception e) {
      log.error("Error Interno con el libro : {} - {}", id, e.getMessage());
      return null;
    }
  }

  public void updateBook(UUID id, String stock) {
    try {
      var headers = new HttpHeaders();
      headers.setContentType(APPLICATION_JSON);
      var requestEntity = new HttpEntity<>(stock, headers);

      restTemplate.exchange(String.format(catalogUrl, "bookstore/v1/books/" + id), PATCH, requestEntity, Book.class).getBody();
    } catch (HttpClientErrorException e) {
      log.error("Error al actualizar el stock del libro: {} - Client Error: {}", id, e.getStatusCode());
    } catch (HttpServerErrorException e) {
      log.error("Error al actualizar el stock del libro: {} - Server Error: {}", id, e.getStatusCode());
    } catch (Exception e) {
      log.error("Error Interno con el libro: {}, - {}", id, e.getMessage());
    }
  }
}
