package com.aboulbaz.UserManagementRestAPIAssignment.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.aboulbaz.UserManagementRestAPIAssignment.dao.PersonDao;
import com.aboulbaz.UserManagementRestAPIAssignment.model.Person;
import com.aboulbaz.UserManagementRestAPIAssignment.model.RoleEnum;
import com.aboulbaz.UserManagementRestAPIAssignment.utils.MediaTypeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Person addPerson(Person person) {
        return this.personDao.save(person);
    }

    public List<Person> getAllPerson() {
        List<Person> users = new ArrayList<Person>();
        Iterable<Person> iterable = this.personDao.findAll();
        iterable.forEach(users::add);
        return users;
    }

    public Optional<Person> findByEmail(String email) {
        return personDao.findByEmail(email);
    };

    public Optional<Person> findByUsername(String username) {
        return personDao.findByUsername(username);
    };

    public Person save(Person user) {
        return this.personDao.save(user);
    }

    public Person getFakeUser() {
        Person user = new Person();
        Faker faker = new Faker();
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        String nums = "0123456789";
        Random rand = new Random();
        StringBuilder phone = new StringBuilder(8);
        for (int j = 0; j < 8; j++)
            phone.append(chars.charAt(rand.nextInt(nums.length())));
        Address address = faker.address();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setBirthDate(faker.date().birthday());
        user.setCity(address.city());
        user.setCountry( address.countryCode().toUpperCase());
        user.setAvatar(faker.avatar().image());
        user.setCompany(faker.company().name());
        user.setJobPosition(faker.job().field());
        user.setMobile((rand.nextBoolean() ? "+2126" : "06") + phone.toString());
        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password(6, 10));
        user.setRole(rand.nextBoolean() ? RoleEnum.ADMIN : RoleEnum.ROLE);
        return user;
    }

    public ResponseEntity<InputStreamResource> generateNewUsers(String counter) throws IOException {
        String fileName = "users-" + counter + ".json";
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        int numberOfUsers = Integer.parseInt(counter);
        ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
        JSONObject object = new JSONObject();
        JSONArray usersToAdd = new JSONArray();
        Faker faker = new Faker();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        String nums = "0123456789";
        Random rand = new Random();
        for (int i = 0; i < numberOfUsers; i++) {
            StringBuilder phone = new StringBuilder(8);
            for (int j = 0; j < 8; j++)
                phone.append(chars.charAt(rand.nextInt(nums.length())));
            Address address = faker.address();
            Map<String, String> user = new HashMap<String, String>();
            user.put("firstName", faker.name().firstName());
            user.put("lastName", faker.name().lastName());
            user.put("birthDate", dateFormat.format(faker.date().birthday()));
            user.put("city", address.city());
            user.put("country", address.countryCode().toUpperCase());
            user.put("avatar", faker.avatar().image());
            user.put("company", faker.company().name());
            user.put("jobPosition", faker.job().field());
            user.put("mobile", (rand.nextBoolean() ? "+2126" : "06") + phone.toString());
            user.put("username", faker.name().username());
            user.put("email", faker.internet().emailAddress());
            user.put("password", faker.internet().password(6, 10));
            user.put("role", rand.nextBoolean() ? RoleEnum.ADMIN.toString() : RoleEnum.ROLE.toString());
            users.add(user);
            usersToAdd.add(user);
        }
        object.put("users", usersToAdd);
        File file = new File("/Users/ayoubboulbaz/Desktop" + "/" + fileName);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(object.toJSONString());
        fileWriter.flush();
        fileWriter.close();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    public ResponseEntity<?> handleFileUpload(MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            Map<String, ArrayList<HashMap<String, String>>> map = mapper.readValue(content, Map.class);
            ArrayList<HashMap<String, String>> users = map.get("users");
            for (HashMap<String, String> person : users) {
                Person newUser = mapper.convertValue(person, Person.class);
                String encodedPass = passwordEncoder.encode(newUser.getPassword());
                newUser.setPassword(encodedPass);
                this.save(newUser);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return ResponseEntity.ok("File uploaded successfully.");
    }
}
