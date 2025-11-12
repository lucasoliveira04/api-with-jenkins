package com.jenkins.apiwithjenkins.service;

import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.mapper.UserMapper;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import com.jenkins.apiwithjenkins.service.getDataUser.IGetData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final Map<String, IGetData> getDataStrategies;

    @Override
    public UserResponse saveUser(UserDto userDto) {
        Users user = UserMapper.toEntity(userDto);
        Users savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        IGetData getData = getDataStrategies.get("getAllFromPostgres");
        Page<Users> users = getData.getUsers(pageable);

        return users.map(UserMapper::toResponse);
    }

    @Override
    public Iterable<Users> findByUserIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }
}