package by.aston.service.impl;

import by.aston.dao.UserDao;
import by.aston.dto.request.RequestAuthorization;
import by.aston.dto.request.UserRequest;
import by.aston.dto.response.UserResponse;
import by.aston.entity.User;
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

import static by.aston.service.impl.TestData.USER_FOR_TEST;
import static by.aston.service.impl.TestData.USER_INCORRECT_UUID;
import static by.aston.service.impl.TestData.USER_REQUEST_FOR_TEST;
import static by.aston.service.impl.TestData.USER_REQUEST_FOR_UPDATE_TEST;
import static by.aston.service.impl.TestData.USER_RESPONSE_FOR_UPDATE_TEST;
import static by.aston.service.impl.TestData.USER_UPDATE_FOR_UPDATE_TEST;
import static by.aston.service.impl.TestData.USER_UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
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

            Optional<User> userInDB = Optional.of(USER_FOR_TEST);

            UserResponse expected = userMapper.toResponse(USER_FOR_TEST);

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
            int expectedSize = 3;

            List<User> usersList = List.of(USER_FOR_TEST,
                    USER_FOR_TEST,
                    USER_FOR_TEST);

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
            UserRequest userRequest = USER_REQUEST_FOR_TEST;

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
    }

    @Nested
    class Update {

        @Test
        void shouldReturnUpdatedUserResponseIfValidUserRequest() {
            // given
            UUID userUuid = USER_UUID;

            UserRequest requestDto = USER_REQUEST_FOR_UPDATE_TEST;

            User userToUpdate = USER_UPDATE_FOR_UPDATE_TEST;

            Optional<User> userInDB = Optional.of(USER_FOR_TEST);

            UserResponse expected = USER_RESPONSE_FOR_UPDATE_TEST;

            when(userDao.findById(userUuid))
                    .thenReturn(userInDB);

            when(userDao.update(userToUpdate))
                    .thenReturn(userToUpdate);

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

            UserRequest requestDto = USER_REQUEST_FOR_UPDATE_TEST;

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

            doNothing().when(userDao).delete(userUuid);

            // when
            userService.delete(userUuid);

            // then
            verify(userDao, times(1))
                    .delete(userUuid);
        }
    }

    @Nested
    class Login {

        @Test
        void shouldReturnSuccessMessageWhenLoginIsValid() {
            // given
            RequestAuthorization authorization = new RequestAuthorization("oleg", "vorona");
            String login = authorization.getLogin();
            String password = authorization.getPassword();
            User user = USER_FOR_TEST;
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

            // when
            String result = userService.login(authorization);

            // then
            assertThat(result).isEqualTo("Данные введены не верно или пользователь не существует!");
        }
    }

    @Nested
    class ChangPassword {

        @Test
        void shouldReturnUpdatedUserResponseWhenChangingPassword() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", "oleg");
            fields.put("oldpassword", "vorona");
            fields.put("newpassword", "zebra");

            String login = fields.get("login").toString();
            String password = fields.get("oldpassword").toString();

            User user = USER_FOR_TEST;

            when(userDao.findUserByLoginAndPassword(login, password)).thenReturn(Optional.of(user));
            when(userDao.update(any(User.class))).thenReturn(user);

            // when
            UserResponse actual = userService.changingPassword(fields);

            // then
            assertThat(actual).isNotNull();
            verify(userDao, times(1)).update(any(User.class));
        }

        @Test
        void shouldThrowExceptionWhenChangingPasswordWithSameOldAndNewPassword() {
            // given
            Map<String, Object> fields = new HashMap<>();
            fields.put("login", "oleg");
            fields.put("oldpassword", "vorona");
            fields.put("newpassword", "vorona");

            // when
            Throwable thrown = catchThrowable(() -> userService.changingPassword(fields));

            // then
            assertThat(thrown).isInstanceOf(ValidException.class);
        }
    }
}