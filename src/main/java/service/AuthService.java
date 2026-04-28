package service;

import com.farmconnect.dto.AuthResponse;
import com.farmconnect.dto.LoginRequest;
import com.farmconnect.entity.User;
import com.farmconnect.repository.UserRepository;
import com.farmconnect.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElse(null);

        if (user == null) {
            return AuthResponse.error("Invalid email or password");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.error("Invalid email or password");
        }

        if (!user.getIsActive()) {
            return AuthResponse.error("Account is inactive");
        }

        String token = jwtUtil.generateToken(() -> user.getEmail());
        return AuthResponse.success(token, user);
    }

    public AuthResponse register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return AuthResponse.error("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.UserRole.BUYER);
        userRepository.save(user);

        String token = jwtUtil.generateToken(() -> user.getEmail());
        return AuthResponse.success(token, user);
    }
}