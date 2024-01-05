package com.example.jungleboarding.login;

import com.example.jungleboarding.login.jwt.RefreshJwtToken;
import com.example.jungleboarding.login.jwt.RefreshJwtTokenRepository;
import com.example.jungleboarding.login.jwt.TokenProvider;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.user.User;
import com.example.jungleboarding.user.UserDto;
import com.example.jungleboarding.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private final RefreshJwtTokenRepository refreshJwtTokenRepository;


    public LoginService(UserRepository userRepository, TokenProvider tokenProvider, RefreshJwtTokenRepository refreshJwtTokenRepository) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.refreshJwtTokenRepository = refreshJwtTokenRepository;
    }

    public LoginResponse login(UserDto userDto){
        Optional<User> checkUser = userRepository.findByUserId(userDto.userId);
        if(checkUser.isEmpty()){
            return null;
        }
        UserDto loginUser = new UserDto(checkUser.get());
//        String encodedPw = new BCryptPasswordEncoder().ma(userDto.userInfo);
        if(new BCryptPasswordEncoder().matches(userDto.userInfo,loginUser.userInfo) == false){
            return null;
        }
        String accessToken = tokenProvider.createAccessToken(String.format("%s:%s", loginUser.userId, loginUser.userRoles));
        String refreshToken = tokenProvider.createRefreshToken();
        RefreshJwtToken refreshJwtToken = new RefreshJwtToken(loginUser.memberId,loginUser.userId,refreshToken);
        Optional<RefreshJwtToken> checkRefresh = refreshJwtTokenRepository.findById(loginUser.memberId);
        if(checkRefresh.isPresent()){
            refreshJwtToken.updateJwtToken(refreshToken);
        }
        refreshJwtTokenRepository.save(refreshJwtToken);
        return new LoginResponse(loginUser.userId, loginUser.userRoles, accessToken,refreshToken);
    }

    public ResponseStatus createUser(UserDto userDto) {
        UserDto createUser = userDto;
        Optional<User> checkUser = userRepository.findByUserId(createUser.getUserId());
        if(checkUser.isEmpty()){
            createUser.setMemberId(UUID.randomUUID().toString().replace("-",""));
            createUser.setUserInfo(new BCryptPasswordEncoder().encode(userDto.userInfo));
            createUser.setUserRoles("USER");
            userRepository.save(createUser.toEntity());
            return ResponseStatus.CREATE_DONE;
        }
        else{
            return ResponseStatus.CREATE_FAIL;
        }
    }

    public ResponseStatus logout(RefreshJwtToken refreshJwtToken) {
        Optional<RefreshJwtToken> logoutToken = refreshJwtTokenRepository.findByRefreshToken(refreshJwtToken);
        if(logoutToken.isEmpty()){
            return ResponseStatus.NOT_FOUND;
        }

        refreshJwtTokenRepository.delete(logoutToken.get());

        return ResponseStatus.OK;
    }
}
