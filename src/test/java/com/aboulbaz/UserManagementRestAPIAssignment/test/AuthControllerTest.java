package com.aboulbaz.UserManagementRestAPIAssignment.test;



import java.net.URI;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aboulbaz.UserManagementRestAPIAssignment.model.LoginCredentials;
import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;
import com.aboulbaz.UserManagementRestAPIAssignment.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.databind.ObjectWriter;
import junit.framework.Assert;

// Thread starvation or clock leap detected

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    @LocalServerPort
	private int port;

    @Autowired
	private MockMvc mockMvc;

    @Autowired
    private PersonService personService = new PersonService();

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void TestGenerateUsersSucess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/generate?counter=2")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    /* @Test
    public void TestAuthLoginSuccess() throws Exception {
        final String baseUrl = "http://localhost:"+port+"/api/auth";
        URI uri = new URI(baseUrl);
        Person mockUser = this.personService.getFakeUser();
        this.personService.addPerson(this.personService.getFakeUser());
        LoginCredentials auth = new LoginCredentials(mockUser.getEmail(), mockUser.getPassword());

        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson= ow.writeValueAsString(auth);
        this.mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().is(200));
    } */
    
}
