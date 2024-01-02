package com.example.jungleboarding.user;

import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("")
    public Response<UserDto> apiGetUsers() {
        DtoList<UserDto> allUsers = userService.getAllUsers();
        return allUsers.toResponse();
    }

    @ResponseBody
    @PostMapping("/user")
    public Response<Integer> apiCreateUser(@RequestBody UserDto userDto) {
        ResponseStatus createResponse = userService.createUser(userDto);

        return new ResponseDto<Integer>(createResponse, createResponse.getCode()).toResponse();

    }

    @ResponseBody
    @PutMapping("/user/{memberId}")
    public Response<UserDto> apiUpdateUser(@PathVariable("memberId") String memberId, @RequestBody UserDto userDto) {
        UserDto updateUserDto = userService.updateUser(memberId, userDto);
        ResponseStatus updateResponse = ResponseStatus.OK;
        if (updateUserDto == null) {
            updateResponse = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(updateResponse, updateUserDto).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/user/{memberId}")
    public Response<UserDto> apiDeleteUser(@PathVariable("memberId") String memberId){
        UserDto deleteUserDto = userService.deleteUser(memberId);

        ResponseStatus deleteResponse = ResponseStatus.OK;
        if(deleteUserDto == null){
            deleteResponse = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(deleteResponse,deleteUserDto).toResponse();
    }

    /*
     * database 초기화용 API
     */
    @ResponseBody
    @DeleteMapping("/users")
    public Response<Integer> apiDeleteAllUser(){
        ResponseStatus deleteResponse = userService.deleteAllUser();

        return new ResponseDto<Integer>(deleteResponse,deleteResponse.getCode()).toResponse();
    }

}