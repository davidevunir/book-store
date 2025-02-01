package com.bs.catalog.repository;

import java.util.UUID;
import com.bs.catalog.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookJpaRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

  Boolean existsByTitleAndAuthor(String title, String author);

  Book findByIdAndActive(UUID id, Boolean active);
}
