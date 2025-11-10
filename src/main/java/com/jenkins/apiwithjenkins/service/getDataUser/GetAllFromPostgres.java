package com.jenkins.apiwithjenkins.service.getDataUser;


import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("getAllFromPostgres")
@RequiredArgsConstructor
public class GetAllFromPostgres implements IGetData{

    private final UserRepository userRepository;

    @Override
    @CachePut(value = "usersCache", key = "'page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize")
    public Page<Users> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
