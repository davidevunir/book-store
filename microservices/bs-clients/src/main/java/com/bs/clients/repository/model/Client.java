package com.bs.clients.repository.model;

import static lombok.AccessLevel.PRIVATE;
import static jakarta.persistence.GenerationType.AUTO;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.util.UUID;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class Client {

  @Id
  @GeneratedValue(strategy = AUTO)
  @Column(name = "ID_CLIENT")
  UUID id;

  @NotNull(message = "El campo firstName es obligatorio")
  String firstName;

  String lastName;

  @NotNull(message = "El campo email es obligatorio")
  String email;

  String address;
  String phone;
  Boolean active;
  LocalDateTime createdAt;

  public void update(Client client) {
    this.firstName = client.getFirstName();
    this.lastName = client.getLastName();
    this.email = client.getEmail();
    this.address = client.getAddress();
    this.phone = client.getPhone();
    this.active = client.getActive();
    this.createdAt = client.getCreatedAt();
  }
}
