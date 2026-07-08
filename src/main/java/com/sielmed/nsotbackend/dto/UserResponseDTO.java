package com.sielmed.nsotbackend.dto;

import com.sielmed.nsotbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private Role role;
    private LocalDateTime createdAt;
}