package com.aboulbaz.UserManagementRestAPIAssignment.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.Operation;

// import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.MediaType;

@RequestMapping("/api/users")
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("add")
    public Person createUser(@RequestBody Person person) {
        try {
            return this.personService.addPerson(person);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping()
    public List<Person> getAllPerson() {
        try {
            return this.personService.getAllPerson();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE', 'ADMIN')")
    @GetMapping("/me")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
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

    @PostMapping(value = "batch", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return this.personService.handleFileUpload(file);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{username}")
    public Person getUserByUsername(@PathVariable("username") String username) {
        return this.personService.findByUsername(username).get();
    }

}