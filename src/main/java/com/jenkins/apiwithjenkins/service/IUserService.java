package com.jenkins.apiwithjenkins.service;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.entity.Users;
import org.springframework.data.domain.Page;

public interface IUserService {

    Users saveUser(UserDto userDto);
    Page<Users> getAllUsers(int page, int size);
}
