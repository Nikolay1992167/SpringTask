package by.aston.entity.listener;

import by.aston.entity.User;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserListener {

    @PrePersist
    public  void prePersist(User user){
        user.setUuid(UUID.randomUUID());
        user.setCreateDate(LocalDateTime.now());
    }
}
