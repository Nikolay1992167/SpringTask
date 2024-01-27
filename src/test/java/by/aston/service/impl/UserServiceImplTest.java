package by.aston.service.impl;

import by.aston.dao.UserDao;
import by.aston.dto.request.RequestAuthorization;
import by.aston.dto.request.UserRequest;
import by.aston.dto.response.UserResponse;
import by.aston.entity.User;
import by.aston.exception.InvalidDataException;
import by.aston.exception.InvalidLoginDataException;
import by.aston.exception.NotFoundException;
import by.aston.exception.ValidException;
import by.aston.mapper.UserMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static by.aston.service.impl.initdata.ConstantsForUser.UPDATE_USER_NAME;
import static by.aston.service.impl.initdata.ConstantsForUser.UPDATE_USER_SURNAME;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_INCORRECT_UUID;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_LOGIN;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_NEW_PASSWORD;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_PASSWORD;
import static by.aston.service.impl.initdata.ConstantsForUser.USER_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Captor
    private ArgumentCaptor<User> captor;

    @Nested
    class FindById {

        @Test
        void shouldReturnExpectedUserByUUID() {
            // given
            UUID userUuid = USER_UUID;
            User user = UserTestData.builder()
                    .build()
                    .getEntity();
            Optional<User> userInDB = Optional.of(user);

            UserResponse expected = userMapper.toResponse(user);

            when(userDao.findById(userUuid))
                    .thenReturn(userInDB);

            // when
            UserResponse actual = userService.findById(userUuid);

            // then
            assertThat(actual).isEqualTo(expected);
            verify(userDao, times(1)).findById(userUuid);
        }

        @Test
        void shouldReturnThrowIfUserNotExistWithUUID() {
            // given
            UUID incorrectUuid = USER_INCORRECT_UUID;

            Optional<User> userInDB = Optional.empty();

            when(userDao.findById(incorrectUuid))
                    .thenReturn(userInDB);

            // when, then
            assertThrows(NotFoundException.class, () -> userService.findById(incorrectUuid));
            verify(userDao, times(1)).findById(incorrectUuid);
        }
    }

    @Nested
    class FindAll {

        @Test
        void shouldReturnListOfUserResponse() {
            // given
            int expectedSize = 1;
            User user = UserTestData.builder()
                    .build()
                    .getEntity();
            List<User> usersList = List.of(user);

            when(userDao.findAll())
                    .thenReturn(usersList);

            // when
            List<UserResponse> actual = userService.findAll();

            // then
            assertThat(actual.size()).isEqualTo(expectedSize);
        }

        @Test
        void shouldCheckEmpty() {
            // given
            when(userDao.findAll())
                    .thenReturn(List.of());

            // when
            List<UserResponse> actual = userService.findAll();

            // then
            assertThat(actual).isEmpty();
        }
    }


    @Nested
    class Save {

        @Test
        void shouldReturnSavedUserResponseIfValidUserRequest() {
            // given
            UserRequest userRequest = UserTestData.builder()
                    .build()
                    .getRequestEntity();

            User expected = userMapper.toUser(userRequest);

            when(userDao.save(expected))
                    .thenReturn(expected);

            // when
            userService.save(userRequest);

            // then
            verify(userDao, times(1)).save(captor.capture());
            User actual = captor.getValue();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        public void shouldReturnThrowValidExceptionWhenUserExists() {
            // given
            UserRequest userRequest = UserTestData.builder()
                    .build()
                    .getRequestEntity();

            User user = UserTestData.builder()
                    .build()
                    .getEntity();

            when(userDao.findUserByLoginAndPassword(userRequest.getLogin(), userRequest.getPassword()))
                    .thenReturn(Optional.of(user));

            // when, then
            assertThatThrownBy(() -> userService.save(userRequest))
                    .isInstanceOf(ValidException.class)
                    .hasMessage("Логин или пароль уже используются!");
        }
    }

    @Nested
    class Update {

        @Test
        void shouldReturnUpdatedUserResponseIfValidUserRequest() {
            // given
            UUID userUuid = USER_UUID;

            UserRequest requestDto = UserTestData.builder()
                    .withName(UPDATE_USER_NAME)
                    .withName(UPDATE_USER_SURNAME)
                    .build()
                    .getRequestEntity();

            User userToUpdate = userMapper.toUser(requestDto);

            User updatedUser = UserTestData.builder()
                    .withName(UPDATE_USER_NAME)
                    .withName(UPDATE_USER_SURNAME)
                    .build()
                    .getEntity();

            Optional<User> userInDB = Optional.of(updatedUser);

            UserResponse expected = userMapper.toResponse(updatedUser);

            when(userDao.findById(userUuid))
                    .thenReturn(userInDB);

            userToUpdate.setId(userInDB.get().getId());
            userToUpdate.setUuid(userInDB.get().getUuid());
            userToUpdate.setCreateDate(userInDB.get().getCreateDate());

            when(userDao.update(userToUpdate))
                    .thenReturn(updatedUser);

            // when
            UserResponse actual = userService.update(userUuid, requestDto);

            // then
            assertThat(actual).isEqualTo(expected);
            verify(userDao, times(1)).findById(userUuid);
        }

        @Test
        void shouldReturnThrowIfUserNotExistWithUUID() {
            // given
            UUID incorrectUUID = USER_INCORRECT_UUID;

            UserRequest requestDto = UserTestData.builder()
                    .withName(UPDATE_USER_NAME)
                    .withName(UPDATE_USER_SURNAME)
                    .build()
                    .getRequestEntity();

            when(userDao.findById(incorrectUUID))
                    .thenReturn(Optional.empty());

            // when, then
            assertThrows(NotFoundException.class, () -> userService.update(incorrectUUID, requestDto));
            verify(userDao, times(1)).findById(incorrectUUID);
            verify(userDao, never()).update(any(User.class));
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteHouseByUUID() {
            // given
            UUID userUuid = USER_UUID;

            User user = UserTestData.builder()
                    .build()
                    .getEntity();

            when(userDao.findById(userUuid))
                    .thenReturn(Optional.of(user));

            doNothing().when(userDao).delete(userUuid);

            // when
            userService.delete(userUuid);

            // then
            verify(userDao, times(1)).findById(userUuid);
            verify(userDao, times(1)).delete(userUuid);
        }

        @Test
        void shouldReturnThrowIfHouseNotExistWithUUID() {
            // given
            UUID incorrectUUID = USER_INCORRECT_UUID;

            // when, then
            assertThrows(NotFoundException.class, () -> userService.delete(incorrectUUID));
            verify(userDao, never()).delete(incorrectUUID);
        }
    }

    @Nested
    class Login {

        @Test
        void shouldReturnSuccessMessageWhenLoginIsValid() {
            // given
            RequestAuthorization authorization = new RequestAuthorization(USER_LOGIN, USER_PASSWORD);
            String login = authorization.getLogin();
            String password = authorization.getPassword();
            User user = UserTestData.builder()
                    .build()
                    .getEntity();
            when(userDao.findUserByLoginAndPassword(login, password)).thenReturn(Optional.of(user));

            // when
            String result = userService.login(authorization);

            // then
            assertThat(result).isEqualTo("Авторизация прошла успешно!");
        }

        @Test
        void shouldReturnErrorMessageWhenLoginIsInvalid() {
            // given
            String loginIncorrect = "";
            String passwordIncorrect = "";

            RequestAuthorization authorization = new RequestAuthorization("", "");

            when(userDao.findUserByLoginAndPassword(loginIncorrect, passwordIncorrect)).thenReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> userService.login(authorization))
                    .isInstanceOf(InvalidLoginDataException.class)
                    .hasMessage("Данные введены не верно или пользователь не существует!");
        }
    }

    @Nested
    class ChangPassword {

        @Test
        void shouldReturnUpdatedUserResponseWhenChangingPassword() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", USER_LOGIN);
            fields.put("oldpassword", USER_PASSWORD);
            fields.put("newpassword", USER_NEW_PASSWORD);

            String login = fields.get("login").toString();
            String password = fields.get("oldpassword").toString();

            User user = UserTestData.builder()
                    .build()
                    .getEntity();

            when(userDao.findUserByLoginAndPassword(login, password)).thenReturn(Optional.of(user));
            when(userDao.update(any(User.class))).thenReturn(user);

            // when
            UserResponse actual = userService.changingPassword(fields);

            // then
            assertThat(actual).isNotNull();
            verify(userDao, times(1)).update(any(User.class));
        }

        @Test
        void shouldThrowExceptionWhenInvalidDataInRequest() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", USER_LOGIN);
            fields.put("oldpassword", USER_PASSWORD);

            assertThatThrownBy(() -> userService.changingPassword(fields))
                    .isInstanceOf(ValidException.class)
                    .hasMessageContaining("Данные введены неверно!");
        }

        @Test
        void shouldThrowExceptionWhenChangingPasswordWithSameOldAndNewPassword() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", USER_LOGIN);
            fields.put("oldpassword", USER_PASSWORD);
            fields.put("newpassword", USER_PASSWORD);

            // when, then
            assertThatThrownBy(() -> userService.changingPassword(fields))
                    .isInstanceOf(InvalidDataException.class);
        }

        @Test
        void shouldThrowExceptionWhenUserWithParametersNotFound() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", USER_LOGIN);
            fields.put("oldpassword", USER_PASSWORD);
            fields.put("newpassword", USER_NEW_PASSWORD);

            when(userDao.findUserByLoginAndPassword(USER_LOGIN, USER_PASSWORD)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.changingPassword(fields))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}