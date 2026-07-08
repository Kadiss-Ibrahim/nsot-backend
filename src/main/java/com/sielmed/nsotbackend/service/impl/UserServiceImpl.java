package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.UserRequestDTO;
import com.sielmed.nsotbackend.dto.UserResponseDTO;
import com.sielmed.nsotbackend.entity.User;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.UserRepository;
import com.sielmed.nsotbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toResponseDTO(user);
    }

    @Override
    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User introuvable avec le username : " + username));
        return toResponseDTO(user);
    }

    @Override
    public UserResponseDTO create(UserRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new DuplicateResourceException(
                    "Un utilisateur avec ce username existe déjà : " + requestDTO.getUsername());
        }
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setRole(requestDTO.getRole());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        return toResponseDTO(userRepository.save(user));
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO requestDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        existing.setUsername(requestDTO.getUsername());
        existing.setRole(requestDTO.getRole());
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        return toResponseDTO(userRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole(), user.getCreatedAt());
    }
}