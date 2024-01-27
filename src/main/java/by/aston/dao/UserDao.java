package by.aston.dao;

import by.aston.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends CrudDao<User, UUID>{

    Optional<User> findUserByLoginAndPassword(String login, String password);
}
