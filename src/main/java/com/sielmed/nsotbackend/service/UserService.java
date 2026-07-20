package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.dto.UserRequestDTO;
import com.sielmed.nsotbackend.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);
    UserResponseDTO findByUsername(String username);
    UserResponseDTO create(UserRequestDTO requestDTO);
    UserResponseDTO update(Long id, UserRequestDTO requestDTO);
    void delete(Long id);
    List<UserResponseDTO> search(String username, com.sielmed.nsotbackend.enums.Role role);
}