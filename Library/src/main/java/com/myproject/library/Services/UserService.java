package com.myproject.library.Services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.library.Models.User;
import com.myproject.library.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(); 
        return BCrypt.hashpw(plainTextPassword, salt);
    }
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    public void saveUser(User user){
        if(user != null && !user.getEmail().isEmpty()){
            var rawPassword = user.passwordHash;
            user.setPasswordHash(hashPassword(rawPassword));
            repository.save(user);
        }
    }

    public boolean login(String email, String password){
        var tempUser = repository.findByEmail(email);
        if(checkPassword(password, tempUser.passwordHash)){
            return true;
        }
        return false;
    }
}
