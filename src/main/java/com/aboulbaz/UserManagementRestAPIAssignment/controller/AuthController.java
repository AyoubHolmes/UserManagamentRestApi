package com.aboulbaz.UserManagementRestAPIAssignment.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aboulbaz.UserManagementRestAPIAssignment.dao.PersonDao;
import com.aboulbaz.UserManagementRestAPIAssignment.model.LoginCredentials;
import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;
import com.aboulbaz.UserManagementRestAPIAssignment.security.JWTUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PersonDao personDao;

    @PostMapping("")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials login) {
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    login.getEmail(), login.getPassword());
            authManager.authenticate(authInputToken);
            Optional<Person> personRes = personDao.findByEmail(login.getEmail());
            if (!personRes.isPresent() || personRes.get() == null)
                personRes = personDao.findByUsername(login.getEmail());
            String token = jwtUtil.generateToken(personRes.get().getEmail());
            return Collections.singletonMap("jwt-token", token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
