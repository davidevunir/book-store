package com.bs.orders.helper;

import static lombok.AccessLevel.PRIVATE;
import static java.util.stream.Collectors.toMap;
import static com.bs.orders.utils.Constants.STOCK;
import static com.bs.orders.utils.Constants.CREATED_STATUS;

import com.bs.orders.repository.impl.BookRepository;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.bs.orders.repository.dto.Book;
import com.bs.orders.repository.dto.Client;
import com.bs.orders.repository.model.Order;
import org.springframework.stereotype.Component;
import com.bs.orders.repository.model.OrderDetail;
import com.bs.orders.repository.impl.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bs.orders.repository.dto.request.OrderRequest;
import com.bs.orders.repository.dto.request.OrderDetailRequest;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderHelper {

  ObjectMapper objectMapper;
  BookRepository bookRepository;
  ClientRepository clientRepository;

  public Client fetchClient(UUID idClient) {
    return clientRepository.getClientById(idClient);
  }

  public Book fetchBook(UUID idBook) {
    return bookRepository.getBookByIdAndActive(idBook, true);
  }

  public Order setOrder(OrderRequest order, Client client, List<Book> books) {
    var orderEntity = Order.builder()
        .idClient(client.id())
        .createdAt(LocalDateTime.now())
        .status(CREATED_STATUS)
        .build();

    var details = order.getDetail().stream()
        .map(det -> {
          var book = books.stream()
              .filter(b -> b.id().equals(det.getIdBook()))
              .findFirst().orElseThrow();
          var subtotal = det.getQuantity() * book.price();

          return OrderDetail.builder()
              .order(orderEntity)
              .idBook(book.id())
              .quantity(det.getQuantity())
              .price(book.price())
              .subtotal(subtotal)
              .build();
        }).toList();

    orderEntity.setOrderDetails(details);
    orderEntity.setTotalAmount(details.stream()
        .mapToDouble(OrderDetail::getSubtotal)
        .sum());

    return orderEntity;
  }

  public void updateStock(List<OrderDetailRequest> purchasedBooks, List<Book> books) {
    var bookMap = books.stream().collect(toMap(Book::id, book -> book));
    for (var order : purchasedBooks) {
      var book = bookMap.get(order.getIdBook());
      try {
        var stock = objectMapper.writeValueAsString(Map.of(STOCK, (book.stock() - order.getQuantity())));
        bookRepository.updateBook(book.id(), stock);
      } catch (Exception e) {
        log.error("Hubo un problema al actualizar el stock del libro: {}", book.title(), e);
      }
    }
  }
}
