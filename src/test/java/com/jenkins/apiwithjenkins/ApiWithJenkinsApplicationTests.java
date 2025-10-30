package com.jenkins.apiwithjenkins;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class ApiWithJenkinsApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

}
