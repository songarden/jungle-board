package com.example.jungleboarding.login;

public record LoginResponse(
        String userId,
        String userRoles,
        String accessToken,
        String refreshToken
) {
}
