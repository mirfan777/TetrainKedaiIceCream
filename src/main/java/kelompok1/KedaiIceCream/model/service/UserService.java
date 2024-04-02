package kelompok1.KedaiIceCream.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.model.LoginUser;
import kelompok1.KedaiIceCream.model.model.RegisterUser;
import kelompok1.KedaiIceCream.model.repository.UserRepository;
import kelompok1.KedaiIceCream.security.BCrypt;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    @Transactional
    public void register(RegisterUser request) {
        log.info(request.getName());

        // validation
        if (request.getPassword() != request.getPassword_confirm()){
            log.info("password tidak sama");
        }else {
            log.info("true");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(0);

        userRepository.save(user);
    }

    @Transactional
    public void login(LoginUser request) {
        log.info(request.getUsername());

        User user = userRepository.findByUsername(request.getUsername());

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            log.info("login berhasil");
        }else {
            log.info("login gagal");
        }


    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user
    public User updateUser(Long id, User userDetails) {
    
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setName(userDetails.getName());
            existingUser.setPassword(userDetails.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
