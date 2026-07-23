package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.dto.LoginRequestDTO;
import com.sielmed.nsotbackend.dto.LoginResponseDTO;
import com.sielmed.nsotbackend.dto.RefreshTokenRequestDTO;
import com.sielmed.nsotbackend.entity.User;
import com.sielmed.nsotbackend.repository.UserRepository;
import com.sielmed.nsotbackend.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(null, null, null, "Invalid username or password"));
        }

        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken, user.getUsername(), "Login successful"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDTO request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.isTokenValid(refreshToken) || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(null, null, null, "Refresh token invalide ou expiré"));
        }

        if (!"refresh".equals(jwtUtil.extractTokenType(refreshToken))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(null, null, null, "Token invalide pour cette opération"));
        }

        String username = jwtUtil.extractUsername(refreshToken);
        String role = jwtUtil.extractRole(refreshToken);

        String newAccessToken = jwtUtil.generateToken(username, role);

        return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, refreshToken, username, "Token rafraîchi"));
    }

}
