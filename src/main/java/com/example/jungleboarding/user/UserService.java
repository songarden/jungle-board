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
}
