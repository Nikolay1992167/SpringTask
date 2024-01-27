package by.aston.service;

import by.aston.dto.request.RequestAuthorization;
import by.aston.dto.request.UserRequest;
import by.aston.dto.response.UserResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    UserResponse findById(UUID uuid);

    List<UserResponse> findAll();

    UserResponse save(UserRequest userRequest);

    UserResponse update(UUID uuid, UserRequest userRequest);

    void delete(UUID uuid);

    String login(RequestAuthorization authorization);

    UserResponse changingPassword(Map<String, Object> fields);
}
