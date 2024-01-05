package com.example.jungleboarding.login.jwt;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Table(name = "refresh_jwt_table")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshJwtToken {
    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "user_id")
    private String userId;



    public RefreshJwtToken(String memberId, String userId, String refreshToken) {
        this.memberId = memberId;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public void updateJwtToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public boolean validRefreshToken(String refreshToken){
        return this.refreshToken.equals(refreshToken);
    }

}
