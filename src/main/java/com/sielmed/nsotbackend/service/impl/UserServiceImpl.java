package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.entity.User;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.UserRepository;
import com.sielmed.nsotbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User introuvable avec le username : " + username));
    }

    @Override
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException(
                    "Un utilisateur avec ce username existe déjà : " + user.getUsername());
        }
        // Le mot de passe ne doit JAMAIS être stocké en clair
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User updated) {
        User existing = findById(id);
        existing.setUsername(updated.getUsername());
        existing.setRole(updated.getRole());
        // Le mot de passe n'est mis à jour QUE s'il est fourni, pour éviter d'écraser
        // le hash existant par un champ vide envoyé depuis un formulaire d'édition.
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }
}