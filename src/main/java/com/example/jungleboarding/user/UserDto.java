package com.example.jungleboarding.user;

import com.example.jungleboarding.util.DataTransferObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@NoArgsConstructor
public class UserDto implements DataTransferObject<User> {
    public String memberId;
    public String userId;
    public String userInfo;
    public String userName;
    public String userEmail;
    public String userRoles;

    public UserDto(String memberId, String userId, String userInfo, String userName, String userEmail, String userRoles) {
        this.memberId = memberId;
        this.userId = userId;
        this.userInfo = userInfo;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRoles = userRoles;
    }

    public UserDto(User user){
        this.memberId = user.getMemberId();
        this.userId = user.getUserId();
        this.userInfo = user.getUserInfo();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userRoles = user.getUserRoles();
    }

    public UserDto userDtoResponse(){
        UserDto userDto = this;
        userDto.setUserInfo(null);
        return userDto;
    }

    @Override
    public User toEntity(){
        return User.builder()
                .memberId(this.memberId)
                .userId(this.userId)
                .userInfo(this.userInfo)
                .userName(this.userName)
                .userEmail(this.userEmail)
                .userRoles(this.userRoles)
                .build();
    }

    @Override
    public DataTransferObject<User> toDto(User user) {
        return new UserDto(user.getMemberId()
                ,user.getUserId()
                ,user.getUserInfo()
                ,user.getUserName()
                ,user.getUserEmail()
                ,user.getUserRoles());
    }
}
