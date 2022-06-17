package com.aboulbaz.UserManagementRestAPIAssignment.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;
import com.aboulbaz.UserManagementRestAPIAssignment.service.PersonService;

@RequestMapping("/api/users")
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("add")
    public Person createUser(@RequestBody Person person) {
        try {
            return this.personService.addPerson(person);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping()
    public List<Person> getAllPerson() {
        try {
            return this.personService.getAllPerson();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/me")
    public Person getUserDetails() {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return personService.findByEmail(email).get();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("generate")
    public ResponseEntity<InputStreamResource> generateNewUsers(@RequestParam("counter") String counter)
            throws IOException {
        try {
            return this.personService.generateNewUsers(counter);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("batch")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return this.personService.handleFileUpload(file);
    }

    @GetMapping("/{username}")
    public Person getUserByUsername(@PathVariable("username") String username) {
        return this.personService.findByUsername(username).get();
    }

}