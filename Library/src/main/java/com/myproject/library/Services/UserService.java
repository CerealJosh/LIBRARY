package com.myproject.library.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

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
        if (user.getRole() != "Librarian") {
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

    @Override
    public User findbyUserId(int id) {
        var user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Value("${upload.path}")
    private String uploadPath;

    public void saveProfilePicture(User user, MultipartFile file) throws IOException {
        String fileName = user.getId() + ".png";// + file.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads/users/");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }
        user.setProfilePhoto(fileName);
        repository.save(user);
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @Override
    public List<User> getUserByRole(String role) {
        return repository.findByRole(role);
    }
}
