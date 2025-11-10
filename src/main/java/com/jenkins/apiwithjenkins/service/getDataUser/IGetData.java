package com.jenkins.apiwithjenkins.service.getDataUser;

import com.jenkins.apiwithjenkins.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGetData {
    Page<Users> getUsers(Pageable pageable);
}
