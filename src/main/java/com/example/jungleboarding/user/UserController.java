package com.example.jungleboarding.user;

import com.example.jungleboarding.annotation.UserAuthorize;
import com.example.jungleboarding.responce.Response;
import com.example.jungleboarding.responce.ResponseDto;
import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@UserAuthorize
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
    @GetMapping("/{memberId}")
    public Response<UserDto> apiGetUser(@PathVariable("memberId") String memberId){
        UserDto userDto = userService.getUser(memberId);
        if(userDto == null){
            return new ResponseDto<UserDto>(ResponseStatus.NOT_FOUND,null).toResponse();
        }

        return new ResponseDto<UserDto>(ResponseStatus.OK,userDto).toResponse();
    }

    @ResponseBody
    @PutMapping("/{memberId}")
    public Response<UserDto> apiUpdateUser(@PathVariable("memberId") String memberId, @RequestBody UserDto userDto) {
        UserDto updateUserDto = userService.updateUser(memberId, userDto);
        ResponseStatus updateResponse = ResponseStatus.OK;
        if (updateUserDto == null) {
            updateResponse = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(updateResponse, updateUserDto.userDtoResponse()).toResponse();
    }

    @ResponseBody
    @DeleteMapping("/{memberId}")
    public Response<UserDto> apiDeleteUser(@PathVariable("memberId") String memberId){
        UserDto deleteUserDto = userService.deleteUser(memberId);

        ResponseStatus deleteResponse = ResponseStatus.OK;
        if(deleteUserDto == null){
            deleteResponse = ResponseStatus.NOT_FOUND;
        }

        return new ResponseDto<UserDto>(deleteResponse,deleteUserDto.userDtoResponse()).toResponse();
    }

    /*
     * database 초기화용 API
     */
    @ResponseBody
    @DeleteMapping("")
    public Response<Integer> apiDeleteAllUser(){
        ResponseStatus deleteResponse = userService.deleteAllUser();

        return new ResponseDto<Integer>(deleteResponse,deleteResponse.getCode()).toResponse();
    }

    @ResponseBody
    @PutMapping("")
    public Response<Integer> apiTest(HttpServletRequest request, @RequestBody UserDto userDto){
        String userId = (String) request.getAttribute("jwtUserId");
        String userName = userDto.userName;

        return null;
    }

}