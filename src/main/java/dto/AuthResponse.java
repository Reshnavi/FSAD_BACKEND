package com.farmconnect.dto;

import com.farmconnect.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private boolean success;
    private String message;
    private String token;
    private UserDTO user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserDTO {
        private Long id;
        private String name;
        private String email;
        private String role;
        private String location;
        private String farmName;
    }

    public static AuthResponse success(String token, User user) {
        return AuthResponse.builder()
            .success(true)
            .message("Login successful")
            .token(token)
            .user(UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .location(user.getLocation())
                .farmName(user.getFarmName())
                .build())
            .build();
    }

    public static AuthResponse error(String message) {
        return AuthResponse.builder()
            .success(false)
            .message(message)
            .build();
    }
}