package by.aston.service.impl;

import by.aston.dto.request.UserRequest;
import by.aston.enams.UserType;
import by.aston.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import static by.aston.service.impl.initdata.ConstantsForUser.USER_CREATEDATE;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_ID;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_LOGIN;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_NAME;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_PASSWORD;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_PHONE;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_SURNAME;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_TYPE;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_UUID;


@Data
@Builder(setterPrefix = "with")
public class UserTestData {

    @Builder.Default
    private Long id = USER_ID;
    @Builder.Default
    private UUID uuid = USER_UUID;
    @Builder.Default
    private String name = USER_NAME;
    @Builder.Default
    private String surname = USER_SURNAME;
    @Builder.Default
    private String login = USER_LOGIN;
    @Builder.Default
    private String password = USER_PASSWORD;
    @Builder.Default
    private Long phone = USER_PHONE;
    @Builder.Default
    private LocalDateTime createDate = USER_CREATEDATE;
    @Builder.Default
    private UserType userType = USER_TYPE;

    public User getEntity() {
        return new User(id, uuid, name, surname, login, password, phone, createDate, userType);
    }

    public UserRequest getRequestEntity() {
        return new UserRequest(name, surname, login, password, phone, userType);
    }
}
