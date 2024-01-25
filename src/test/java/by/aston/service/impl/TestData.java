package by.aston.service.impl;

import by.aston.dto.request.UserRequest;
import by.aston.dto.response.UserResponse;
import by.aston.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestData {

    public static final UUID USER_UUID = UUID.fromString("0699cfd2-9fb7-4483-bcdf-194a2c6b7fe6");
    public static final UUID USER_INCORRECT_UUID = UUID.fromString("0699cfd2-9fb7-4483-bcdf-194a2c6b7f88");

    public static final User USER_FOR_TEST = User.builder()
            .uuid(USER_UUID)
            .name("Олег")
            .surname("Ворона")
            .login("oleg")
            .password("vorona")
            .phone(375447894561L)
            .createDate(LocalDateTime.of(2024, 1, 24, 12, 30, 0))
            .build();

    public static final UserRequest USER_REQUEST_FOR_TEST = UserRequest.builder()
            .name("Олег")
            .surname("Ворона")
            .login("oleg")
            .password("vorona")
            .phone(375447894561L)
            .build();

    public static final UserRequest USER_REQUEST_FOR_UPDATE_TEST = UserRequest.builder()
            .name("Евгений")
            .surname("Рыба")
            .login("oleg")
            .password("vorona")
            .phone(375447894561L)
            .build();

    public static final User USER_UPDATE_FOR_UPDATE_TEST = User.builder()
            .uuid(USER_UUID)
            .name("Евгений")
            .surname("Рыба")
            .login("oleg")
            .password("vorona")
            .phone(375447894561L)
            .createDate(LocalDateTime.of(2024, 1, 24, 12, 30, 0))
            .build();

    public static final UserResponse USER_RESPONSE_FOR_UPDATE_TEST = UserResponse.builder()
            .uuid(USER_UUID)
            .name("Евгений")
            .surname("Рыба")
            .phone(375447894561L)
            .createDate(LocalDateTime.of(2024, 1, 24, 12, 30, 0))
            .build();
}
