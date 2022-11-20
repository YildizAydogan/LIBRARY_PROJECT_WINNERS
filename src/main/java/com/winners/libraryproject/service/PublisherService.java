package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.Publisher.PublisherDTO;
import com.winners.libraryproject.entity.Publisher;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.payload.messages.ErrorMessage;
import com.winners.libraryproject.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class PublisherService {

    private PublisherRepository repository;






    public Publisher createPublisher(PublisherDTO publisherDTO) {

        Publisher publisher= new Publisher();

        publisher.setName(publisherDTO.getName());

        repository.save(publisher);
        return  publisher;
    }

    public Page<PublisherDTO> getPublisherPage(Pageable pageable) {
        Page<Publisher> publishers = repository.findAll(pageable);
        Page<PublisherDTO> dtoPage = publishers.map(new Function<Publisher, PublisherDTO>() {
            @Override
            public PublisherDTO apply(Publisher publisher) {
                PublisherDTO publisherDTO=new PublisherDTO();
                publisherDTO.setName(publisher.getName());
                publisherDTO.setId(publisher.getId());
                return publisherDTO;
            }
        });

        return dtoPage;
    }


    public PublisherDTO findById(Long id) {
        Publisher publisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        PublisherDTO publisherDTO=new PublisherDTO();
        publisherDTO.setName(publisher.getName());
        publisherDTO.setId(publisher.getId());
        return publisherDTO;
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        Publisher foundPublisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        if(foundPublisher.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        foundPublisher.setId(id);
        foundPublisher.setName(publisher.getName());
        repository.save(foundPublisher);
        return foundPublisher;
    }


    public Publisher deleteById(Long id) {
        Publisher publisher = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        if(publisher.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if(!publisher.getBooks().isEmpty()) {
            throw  new ResourceNotFoundException("You cannot delete an publisher who has a book");
        }
        repository.deleteById(id);
        return publisher;
    }





}