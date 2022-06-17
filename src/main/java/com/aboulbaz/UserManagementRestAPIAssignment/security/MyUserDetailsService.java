package com.aboulbaz.UserManagementRestAPIAssignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aboulbaz.UserManagementRestAPIAssignment.dao.PersonDao;
import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;

import java.util.Arrays;
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
            throw new RuntimeException("Could not findUser with email = " + email);
        Person user = personRes.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ROLE"), new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
