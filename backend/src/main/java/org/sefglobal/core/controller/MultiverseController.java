package org.sefglobal.core.controller;

import org.sefglobal.core.exception.ResourceNotFoundException;
import org.sefglobal.core.model.Ambassador;
import org.sefglobal.core.model.Event;
import org.sefglobal.core.model.University;
import org.sefglobal.core.repository.AmbassadorRepository;
import org.sefglobal.core.repository.EventRepository;
import org.sefglobal.core.repository.UniversityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.Optional;

@RestController
public class MultiverseController {
    Logger logger = LoggerFactory.getLogger(MultiverseController.class);
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/universities")
    public Iterable<University> listUniversities() {
        return universityRepository.findAll();
    }

    @GetMapping("/universities/{id}")
    public Optional<University> getUniversityById(@PathVariable long id) {
        return universityRepository.findById(id);
    }

    @PostMapping("/universities")
    public University createUniversity(@Valid @RequestBody University university) {
        return universityRepository.save(university);
    }

    @PutMapping("/universities/{id}")
    public University updateUniversity(@PathVariable long id,
            @Valid @RequestBody University university) throws ResourceNotFoundException {
        if (universityRepository.existsById(id)) {
            return universityRepository.save(university);
        } else {
            String message = "University does not exists for university id : " + id;
            logger.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    //todo: Implement an endpoint to delete university (change active status)

    @GetMapping("/ambassadors")
    public Iterable<Ambassador> listAmbassador() {
        return ambassadorRepository.findAll();
    }

    @GetMapping("/ambassadors/{id}")
    public Optional<Ambassador> getAmbassadorById(@PathVariable long id) {
        return ambassadorRepository.findById(id);
    }

    @PostMapping("/universities/{id}/ambassadors")
    public Ambassador createAmbassador(@PathVariable long id,
            @Valid @RequestBody Ambassador ambassador) throws ResourceNotFoundException {
        return universityRepository.findById(id).map(university -> {
            ambassador.setUniversity(university);
            return ambassadorRepository.save(ambassador);
        }).orElseThrow(() -> new ResourceNotFoundException("University not found"));
    }

    //todo: Implement an endpoint to list all ambassadors in a university
    //todo: Implement an endpoint to delete ambassador (change active status)
    //todo: Implement an endpoint to update ambassador

    @GetMapping("/events")
    public Iterable<Event> listEvents() {
        return eventRepository.findAll();
    }

    @GetMapping("/events/{id}")
    public Optional<Event> getEventById(@PathVariable long id) {
        return eventRepository.findById(id);
    }

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    //todo: Implement an endpoint to update an event
    //todo: Implement an endpoint to delete an event (change active status)

}
