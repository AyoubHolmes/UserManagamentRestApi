package com.aboulbaz.UserManagementRestAPIAssignment.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aboulbaz.UserManagementRestAPIAssignment.controller.AuthController;
import com.aboulbaz.UserManagementRestAPIAssignment.controller.PersonController;


@SpringBootTest
public class SmokeTest {
    @Autowired
	private PersonController personController;
    @Autowired // pretty interesting
	private AuthController authController;

	@Test
	public void authControllerLoads() throws Exception {
		assertThat(authController).isNotNull();
	}

    @Test
	public void personControllerLoads() throws Exception {
		assertThat(personController).isNotNull();
	}
}
