package com.jenkins.apiwithjenkins.config;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseIndexCreator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseIndexCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createIndexes() {
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_users_phone ON users (phone)");
        jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON users (email)");
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_address_user_id ON address (user_id)");
    }
}
