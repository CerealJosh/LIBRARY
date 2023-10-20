package com.myproject.library.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.myproject.library.Models.User;
import com.myproject.library.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        var encoder = new BCryptPasswordEncoder();
        var hashedPassword = encoder.encode(user.getPassword());
        if(user.getRole() != "Librarian"){
            user.setRole("Reader");
        }
        user.setPassword(hashedPassword);
        repository.save(user);
        return user;
    }

    public User loadUserByEmail(String username) throws UsernameNotFoundException {
        // CreateAdmin();
        var tempUser = repository.findByEmail(username);
        if (tempUser == null)
            throw new UsernameNotFoundException(username);
        return tempUser;
    }


	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}
}
