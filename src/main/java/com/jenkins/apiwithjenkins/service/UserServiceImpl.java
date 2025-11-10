package com.jenkins.apiwithjenkins.service;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.mapper.UserMapper;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public Users saveUser(UserDto userDto) {
        Users user = UserMapper.toEntity(userDto);
        return userRepository.save(user);
    }

    @Override
    public Page<Users> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Iterable<Users> findByUserIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }
}
