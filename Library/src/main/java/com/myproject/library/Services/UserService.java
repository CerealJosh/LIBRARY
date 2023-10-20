package com.myproject.library.Services;

import org.springframework.beans.factory.annotation.Autowired;
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

    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     // CreateAdmin();
    //     var tempUser = repository.findByEmail(username);
    //     if (tempUser == null)
    //         throw new UsernameNotFoundException(username);
    //     return tempUser;
    // }
    // private boolean checkIfEmailUsed(String email){
    //     return repository.findByEmail(email) !=null ? true : false;
    // }
    // public User checkUserAndPassword(String username, String password){
    //     var user = repository.findByEmail(username);
    //     var encoder = new BCryptPasswordEncoder();  
    //     System.out.println(encoder.matches(password, user.getPassword())); 
    //     if(encoder.matches(password, user.getPassword())){
    //         return user;    
    //     }
    //      System.out.println(user.getPassword());
    //         System.out.println(encoder.encode(password));
    //     return null;
    // }

	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}
}
