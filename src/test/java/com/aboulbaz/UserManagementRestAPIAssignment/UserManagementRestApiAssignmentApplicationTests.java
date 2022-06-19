package com.aboulbaz.UserManagementRestAPIAssignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class UserManagementRestApiAssignmentApplicationTests {

	@Test
	void contextLoads() {
	}

}
