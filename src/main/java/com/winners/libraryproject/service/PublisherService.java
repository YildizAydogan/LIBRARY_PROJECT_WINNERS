package com.winners.libraryproject.service;

import com.winners.libraryproject.entity.Publisher;
import com.winners.libraryproject.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }
}
