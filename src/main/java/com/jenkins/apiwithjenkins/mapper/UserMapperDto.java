package com.jenkins.apiwithjenkins.mapper;

import com.jenkins.apiwithjenkins.entity.Address;
import com.jenkins.apiwithjenkins.entity.Users;

import java.util.List;

public record UserMapperDto() {
    public Users toEntity(Users users) {
        Users user = Users.builder()
                .name(users.getName())
                .email(users.getEmail())
                .cpf(users.getCpf())
                .phone(users.getPhone())
                .birthDate(users.getBirthDate())
                .build();

        List<Address> addresses = users.getAddress().stream()
                .map(addr -> Address.builder()
                        .street(addr.getStreet())
                        .city(addr.getCity())
                        .state(addr.getState())
                        .zipCode(addr.getZipCode())
                        .country(addr.getCountry())
                        .user(user)
                        .build())
                .toList();
        user.setAddress(addresses);
        return user;
    }
}
