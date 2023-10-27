package com.myproject.library.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.library.Models.User;

public interface IUserService {

	User saveUser(User user);

	void removeSessionMessage();

	User findbyUserId(int id);

	List<User> getUserByRole(String role);

	List<User> getAllUsers();

	void saveProfilePicture(User user, MultipartFile file) throws IOException;

	User loadUserByEmail(String username) throws UsernameNotFoundException;
}
