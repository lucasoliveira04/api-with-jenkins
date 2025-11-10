package com.jenkins.apiwithjenkins.service.getDataUser;

import com.jenkins.apiwithjenkins.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("getAllFromRedis")
@RequiredArgsConstructor
public class GetAllFromRedis implements IGetData{

    @Override
    @Cacheable(value = "usersCache", key = "'page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize")
    public Page<Users> getUsers(Pageable pageable) {
        return new PageImpl<>(List.of(), pageable, 0);
    }
}
