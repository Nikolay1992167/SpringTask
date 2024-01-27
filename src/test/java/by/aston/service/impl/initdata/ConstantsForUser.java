package by.aston.service.impl.initdata;

import by.aston.enams.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConstantsForUser {

    public static final Long USER_ID = 1L;
    public static final UUID USER_UUID = UUID.fromString("cfe5c508-7b9a-465e-8beb-339255920191");
    public static final String USER_NAME = "Jon";
    public static final String USER_SURNAME = "Lebovski";
    public static final String USER_LOGIN = "strawberry";
    public static final String USER_PASSWORD = "blueberry";
    public static final Long USER_PHONE = 375445556677L;
    public static final LocalDateTime USER_CREATEDATE = LocalDateTime.of(2023, 1, 13, 18, 30, 0);
    public static final UserType USER_TYPE = UserType.USER;
    public static final UUID USER_INCORRECT_UUID = UUID.fromString("cfe5c508-7b9a-465e-8beb-339255920195");
    public static final String UPDATE_USER_NAME = "Alex";
    public static final String UPDATE_USER_SURNAME = "Drobush";
    public static final String USER_NEW_PASSWORD = "azerot";
}
