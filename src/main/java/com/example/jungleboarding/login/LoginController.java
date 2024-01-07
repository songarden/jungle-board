package com.example.jungleboarding.login;

import com.example.jungleboarding.annotation.UserAuthorize;
import com.example.jungleboarding.login.jwt.RefreshJwtToken;
import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.user.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ResponseBody
    @PostMapping("/sign/login")
    public Response<LoginResponse> apiLogin (@RequestBody UserDto userDto){
        LoginResponse loginResponse = loginService.login(userDto);
        ResponseStatus responseStatus = ResponseStatus.OK;
        if(loginResponse == null){
            responseStatus = ResponseStatus.UNAUTHORIZED;
        }

        return new ResponseDto<LoginResponse>(responseStatus,loginResponse).toResponse();
    }

    @ResponseBody
    @PostMapping("/sign/join")
    public Response<Integer> apiJoin(@RequestBody UserDto userDto) {
        ResponseStatus createResponse = loginService.createUser(userDto);

        return new ResponseDto<Integer>(createResponse, createResponse.getCode()).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/logout")
    @UserAuthorize
    public Response<Integer> apiLogout(@RequestBody RefreshJwtToken refreshJwtToken){
        ResponseStatus logoutResponse = loginService.logout(refreshJwtToken);

        return new ResponseDto<Integer>(logoutResponse, logoutResponse.getCode()).toResponse();
    }
}
