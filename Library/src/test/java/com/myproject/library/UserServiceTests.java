package com.myproject.library;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.myproject.library.Services.UserService;

@SpringBootTest
public class UserServiceTests {
    @Test
	void verifyHashes() {
		String password = "PPP";
		var e1 = UserService.hashPassword(password);
		assertTrue(UserService.checkPassword(password, e1));
		assertFalse(UserService.checkPassword("OOO", e1));
	}
}
