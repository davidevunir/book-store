package com.bs.orders.repository;

import java.util.UUID;
import com.bs.orders.repository.dto.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-catalog", url = "${service.catalog.url}")
public interface IBookRepository {

  @GetMapping("/bookstore/v1/books/{id}")
  Book getBookByIdAndActive(@PathVariable("id") UUID id, @RequestParam(required = false) Boolean active);

  @PatchMapping("/bookstore/v1/books/{id}")
  Book updateBook(@PathVariable("id") UUID id, @RequestBody String item);
}
