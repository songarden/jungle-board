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
    public Response<UserDto> apiGetUsers() {
        DtoList<UserDto> users = userService.getAllUsers();
        return users.toResponse();
    }

    @ResponseBody
    @PostMapping("/user")
    public Response<Integer> apiCreateUser(@RequestBody UserDto userDto) {
        ResponseStatus createStatus = userService.createUser(userDto);

        return new ResponseDto<Integer>(createStatus, createStatus.getCode()).toResponse();

    }

    @ResponseBody
    @PutMapping("/user/{memberId}")
    public Response<UserDto> apiUpdateUser(@PathVariable("memberId") String memberId, @RequestBody UserDto userDto) {
        UserDto updateUserDto = userService.updateUser(memberId, userDto);
        ResponseStatus responseStatus = ResponseStatus.OK;
        if (updateUserDto == null) {
            responseStatus = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(responseStatus, updateUserDto).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/user/{memberId}")
    public Response<UserDto> apiDeleteUser(@PathVariable("memberId") String memberId){
        UserDto deleteUserDto = userService.deleteUser(memberId);

        ResponseStatus responseStatus = ResponseStatus.OK;
        if(deleteUserDto == null){
            responseStatus = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(responseStatus,deleteUserDto).toResponse();
    }

    /*
     * database 초기화용 Service
     */
    @ResponseBody
    @DeleteMapping("/users")
    public Response<Integer> apiDeleteAllUser(){
        ResponseStatus deleteResponseStatus = userService.deleteAllUser();

        return new ResponseDto<Integer>(deleteResponseStatus,deleteResponseStatus.getCode()).toResponse();
    }

}