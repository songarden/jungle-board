package com.example.jungleboarding.login;

public record LoginResponse(
        String memberId,
        String userId,
        String userRoles,
        String accessToken,
        String refreshToken
) {
}
