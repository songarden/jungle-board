package com.example.jungleboarding.user;

import com.example.jungleboarding.responce.ResponseStatus;
import com.example.jungleboarding.util.DtoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public DtoList<UserDto> getAllUsers() {
        DtoList<UserDto> allUserList = new DtoList<>(userRepository.findAll());
        return allUserList;
    }

    public ResponseStatus createUser(UserDto userDto) {
        UserDto createUser = userDto;
        Optional<User> checkUser = userRepository.findByUserId(createUser.getUserId());
        if(checkUser.isEmpty()){
            createUser.setMemberId(UUID.randomUUID().toString().replace("-",""));
            createUser.setUserRoles("USER");
            userRepository.save(createUser.toEntity());
            return ResponseStatus.CREATE_DONE;
        }
        else{
            return ResponseStatus.CREATE_FAIL;
        }
    }


    public UserDto updateUser(String memberId, UserDto userDto) {
        Optional<User> checkUpdateUser = userRepository.findById(memberId);
        if(checkUpdateUser.isEmpty()){
            return null;
        }
        if(userDto.getMemberId() != memberId){
            return null;
        }
        Optional<User> checkValidUserId = userRepository.findByUserId(userDto.getUserId());
        if(checkValidUserId.isPresent()){
            return null;
        }

        UserDto updateUser = new UserDto(checkUpdateUser.get());
        if(userDto.userId != null){
            updateUser.setUserId(userDto.userId);
        }
        if(userDto.userEmail != null){
            updateUser.setUserEmail(userDto.userEmail);
        }
        if(userDto.userInfo != null){
            updateUser.setUserInfo(userDto.userInfo);
        }

        userRepository.save(updateUser.toEntity());

        return updateUser;


    }

    public UserDto deleteUser(String memberId) {
        Optional<User> checkDeleteUser = userRepository.findById(memberId);
        if(checkDeleteUser.isEmpty()){
            return null;
        }
        UserDto deleteUser = new UserDto(checkDeleteUser.get());
        userRepository.delete(deleteUser.toEntity());

        return deleteUser;
    }

    /* database 초기화용 Service */
    public ResponseStatus deleteAllUser() {
        userRepository.deleteAll();

        return ResponseStatus.OK;
    }


}
