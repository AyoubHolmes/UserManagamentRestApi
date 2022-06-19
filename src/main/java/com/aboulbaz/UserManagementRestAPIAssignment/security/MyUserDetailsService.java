package com.aboulbaz.UserManagementRestAPIAssignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.aboulbaz.UserManagementRestAPIAssignment.dao.PersonDao;
import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonDao personDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws RuntimeException {

        Optional<Person> personRes = personDao.findByEmail(email);
        if (!personRes.isPresent() || personRes.get() == null)
            personRes = personDao.findByUsername(email);
        if (!personRes.isPresent() || personRes.get() == null)
            throw new RuntimeException("Could not findUser with email or username = " + email);

        Person user = personRes.get();
        System.out.println(user.getRole().toString());
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())));
    }
}
