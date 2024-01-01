package com.example.jungleboarding.user;

import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/users")
    public Response<UserDto> apiGetUsers(){
        DtoList<UserDto> users = userService.getAllUsers();
        return users.toResponse();
    }

    @ResponseBody
    @PostMapping("/user")
    public Response<Integer> apiCreateUser(@RequestBody UserDto userDto){
        ResponseStatus createStatus = userService.createUser(userDto);

        return new ResponseDto<Integer>(createStatus,createStatus.getCode()).toResponse();

    }


}
