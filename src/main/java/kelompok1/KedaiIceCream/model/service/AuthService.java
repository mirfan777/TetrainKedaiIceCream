package kelompok1.KedaiIceCream.model.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kelompok1.KedaiIceCream.model.dto.LoginUser;
import kelompok1.KedaiIceCream.model.dto.RegisterUser;
import kelompok1.KedaiIceCream.model.entity.User;
import kelompok1.KedaiIceCream.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterUser request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(0); // Set the default role for new users
        userRepository.save(user);
    }

    @Transactional
    public User authenticate(LoginUser request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null) {
            boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
            log.info("Entered password: {}", request.getPassword());
            log.info("Stored password: {}", user.getPassword());
            log.info("Matches: {}", matches);
            if (matches) {
                return user;
            }
        }
        log.info(request.getUsername());
        return null;
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
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                getAuthorities(user.getRole())
        );
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(Integer role) {
         List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    if (role == 1) {
        authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
    } else if (role == 2) {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    } else {
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    return authorities;
    }
}