package com.jenkins.apiwithjenkins.mapper;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.entity.Address;
import com.jenkins.apiwithjenkins.entity.Users;

import java.util.List;

public class UserMapper {

    public static Users toEntity(UserDto userDto) {
        Users user = Users.builder()
                .name(userDto.name())
                .email(userDto.email())
                .phone(userDto.phone())
                .birthDate(userDto.birthDate())
                .build();

        List<Address> addresses = userDto.addresses().stream()
                .map(dto -> Address.builder()
                        .city(dto.city())
                        .country(dto.country())
                        .state(dto.state())
                        .street(dto.street())
                        .zipCode(dto.zipCode())
                        .user(user)
                        .build())
                .toList();

        user.setAddress(addresses);
        return user;
    }
}
