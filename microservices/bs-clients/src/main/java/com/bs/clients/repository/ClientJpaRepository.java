package com.bs.clients.repository;

import java.util.UUID;
import com.bs.clients.repository.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientJpaRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {

  Boolean existsByEmail(String email);
}
