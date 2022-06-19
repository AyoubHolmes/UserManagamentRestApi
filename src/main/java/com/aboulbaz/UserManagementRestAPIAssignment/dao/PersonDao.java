package com.aboulbaz.UserManagementRestAPIAssignment.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;

@Repository
public interface PersonDao extends CrudRepository<Person, Integer> {

    public Optional<Person> findByEmail(String email);

    public Optional<Person> findByUsername(String username);
}
