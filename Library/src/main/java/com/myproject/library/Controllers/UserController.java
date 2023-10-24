package com.myproject.library.Controllers;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.library.Models.User;
import com.myproject.library.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userService.loadUserByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@GetMapping("/")
	public String index() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String r = auth.getAuthorities().toString();
		if (!r.equals("[Librarian]")) {
			return "index";
		}
		return "index-admin";
	}

	@GetMapping("/index-admin")
	public String indexAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String r = auth.getAuthorities().toString();
		if (!r.equals("[Librarian]")) {
			return "index";
		}
		return "index-admin";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/user/profile")
	public String profile(Principal p, Model m) {
		String email = p.getName();
		User user = userService.loadUserByEmail(email);
		m.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/user/home")
	public String home() {
		return "index";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, Model m) {

		User u = userService.saveUser(user);

		if (u != null) {
			session.setAttribute("msg", "Register successfully");

		} else {
			session.setAttribute("msg", "Something wrong server");
		}
		return "index";
	}

	@GetMapping("NotAuthorized")
	public String NotAuthorized() {
		return "NotAuthorized";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, Principal p, Model model) {
		try {
			String email = p.getName();
			User user = userService.loadUserByEmail(email);
			model.addAttribute("user", user);
			userService.saveProfilePicture(user, file);
			return ResponseEntity.ok("File uploaded successfully!");
		} catch (IOException e) {
			// e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file!");
		}
	}

	@GetMapping("/user/allUsers")
	public String getAllUsers(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String r = auth.getAuthorities().toString();
		if (!r.equals("[Librarian]")) {
			return "NotAuthorized";
		}
		var Users = userService.getAllUsers();
		model.addAttribute("users", Users);
		return "allUsers";
	}
}
