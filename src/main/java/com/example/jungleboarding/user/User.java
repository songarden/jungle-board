package com.example.jungleboarding.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Table(name = "user_table")
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "user_id")
    private String userId;

    @JsonIgnore
    @Column(name = "user_info")
    private String userInfo;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_roles")
    private String userRoles;

    @Builder
    public User(String memberId, String userId, String userInfo, String userName, String userEmail, String userRoles) {
        this.memberId = memberId;
        this.userId = userId;
        this.userInfo = userInfo;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRoles = userRoles;
    }
}
