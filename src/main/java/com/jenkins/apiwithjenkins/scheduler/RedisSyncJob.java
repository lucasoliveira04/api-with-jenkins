package com.jenkins.apiwithjenkins.scheduler;

import com.jenkins.apiwithjenkins.entity.Users;
import com.jenkins.apiwithjenkins.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSyncJob {

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 */5 * * * *")
    public void syncUsersToRedis(){
        List<Users> users = userRepository.findAllWithAddresses();

        if (cacheManager.getCache("users") != null) {
            cacheManager.getCache("users").clear();
        }

        users.forEach(user -> {
            if (cacheManager.getCache("users") != null) {
                cacheManager.getCache("users").put(user.getId(), user);
            }
        });

        log.info("Redis sincronizado com banco " + users.size() + " usuários atualizados.");
    }
}
