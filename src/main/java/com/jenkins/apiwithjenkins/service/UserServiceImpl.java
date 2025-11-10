package com.jenkins.apiwithjenkins.service;

import com.jenkins.apiwithjenkins.dto.AddressResponse;
import com.jenkins.apiwithjenkins.dto.UserDto;
import com.jenkins.apiwithjenkins.dto.UserResponse;
import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.mapper.UserMapper;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import com.jenkins.apiwithjenkins.service.getDataUser.IGetData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.User;
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

        IGetData getData = getDataStrategies.get("getAllFromRedis");
        Page<Users> users = getData.getUsers(pageable);

        if (users.isEmpty()){
            log.info("Cache vazio, buscando no banco de dados.");
            IGetData dbStrategy = getDataStrategies.get("getAllFromPostgres");
            users = dbStrategy.getUsers(pageable);
        }

        return users.map(user -> new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getBirthDate(),
                user.getAddress() != null && !user.getAddress().isEmpty()
                        ? new AddressResponse(
                        user.getAddress().get(0).getStreet(),
                        user.getAddress().get(0).getCity(),
                        user.getAddress().get(0).getState(),
                        user.getAddress().get(0).getZipCode(),
                        user.getAddress().get(0).getCountry()
                )
                        : null
        ));
    }

    @Override
    public Iterable<Users> findByUserIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }
}