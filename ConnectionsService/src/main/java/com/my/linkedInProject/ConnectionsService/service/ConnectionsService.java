package com.my.linkedInProject.ConnectionsService.service;

import com.my.linkedInProject.ConnectionsService.entity.Person;
import com.my.linkedInProject.ConnectionsService.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionsService {

    private static final Logger log = LoggerFactory.getLogger(ConnectionsService.class);

    private final PersonRepository personRepository;
    
    public ConnectionsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        log.info("Getting first degree connections of user with ID: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }

}
