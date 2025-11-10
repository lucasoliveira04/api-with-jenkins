package com.jenkins.apiwithjenkins.repository;

import com.jenkins.apiwithjenkins.entity.Users;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
}
