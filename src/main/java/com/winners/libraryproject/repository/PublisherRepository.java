package com.winners.libraryproject.repository;

import com.winners.libraryproject.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
