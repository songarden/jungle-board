package com.example.jungleboarding.login.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshJwtTokenRepository extends JpaRepository<RefreshJwtToken,String> {
    Optional<RefreshJwtToken> findByUserId(String userId);

    Optional<RefreshJwtToken> findByRefreshToken(RefreshJwtToken refreshJwtToken);
}
