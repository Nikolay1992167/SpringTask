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
import by.aston.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Finds one {@link UserResponse} by UUID.
     *
     * @param uuid the field uuid of the {@link User}.
     * @return UserResponse with the specified UUID and mapped from User entity.
     * @throws NotFoundException if User is not exists by finding it by UUID.
     */
    @Override
    public UserResponse findById(UUID uuid) {

        UserResponse userResponse = userDao.findById(uuid)
                .map(userMapper::toResponse)
                .orElseThrow(() -> NotFoundException.of(User.class, uuid));
        log.info("Service method findById: {}", userResponse);

        return userResponse;
    }

    /**
     * Finds all {@link UserResponse}.
     *
     * @return mapped from entity to dto list of all UserResponse.
     */
    @Override
    public List<UserResponse> findAll() {

        List<UserResponse> userResponses = userDao.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
        log.info("Service method findAll: {}", userResponses);

        return new ArrayList<>(userResponses);
    }

    /**
     * Saves one {@link User} and checking the login and the password if exist in DB.
     * @param userRequest the {@link UserRequest} which will be mapped to User
     *                     and saved in database by dao.
     * @return the saved {@link UserResponse} which was mapped from User entity.
     */
    @Override
    public UserResponse save(UserRequest userRequest) {

        String userLogin = userRequest.getLogin();
        String userPassword = userRequest.getPassword();

        Optional<User> userInDB = userDao.findUserByLoginAndPassword(userLogin, userPassword);
        if (userInDB.isPresent()){
            throw new ValidException("Логин или пароль уже используются!");
        }

        User userToSave = userMapper.toUser(userRequest);

        User savedUser = userDao.save(userToSave);

        UserResponse userResponse = userMapper.toResponse(savedUser);
        log.info("Service method save: {}", userResponse);

        return userResponse;
    }

    /**
     * Updates one {@link User}.
     * @param uuid the field uuid of the {@link User}.
     * @param userRequest the {@link UserRequest} which will be mapped to User and
     *                     updated in database by dao.
     * @return the updated {@link UserResponse} which was mapped from User entity.
     * @throws NotFoundException if User is not exists by finding it by UUID.
     */
    @Override
    public UserResponse update(UUID uuid, UserRequest userRequest) {

        User userToUpdate = userMapper.toUser(userRequest);

        User userInDB = userDao.findById(uuid)
                .orElseThrow(() -> NotFoundException.of(User.class, uuid));

        userToUpdate.setId(userInDB.getId());
        userToUpdate.setUuid(uuid);
        userToUpdate.setCreateDate(userInDB.getCreateDate());

        User updatedUser = userDao.update(userToUpdate);

        UserResponse userResponse = userMapper.toResponse(updatedUser);
        log.info("Service method update: {}", userResponse);

        return userResponse;
    }

    /**
     * Deletes one {@link User} by UUID.
     *
     * @param uuid the field of the User.
     * @throws NotFoundException if User is not exists by finding it by UUID.
     */
    @Override
    public void delete(UUID uuid) {

        User userInDB = userDao.findById(uuid)
                .orElseThrow(() -> NotFoundException.of(User.class, uuid));

        userDao.delete(userInDB.getUuid());
        log.info("Service method delete");
    }

    /**
     * Performs a check login and pasword of {@link RequestAuthorization}.
     * @param authorization the {@link RequestAuthorization} when contain
     *                      login parameters.
     * @return a message containing a response.
     */
    @Override
    public String login(RequestAuthorization authorization) {

        String login = authorization.getLogin();
        String password = authorization.getPassword();

        Optional<User> userInDB = userDao.findUserByLoginAndPassword(login, password);

        if (userInDB.isPresent()) {
            return "Авторизация прошла успешно!";
        } else {
            throw new InvalidLoginDataException();
        }
    }

    /**
     * Updates the password of User.
     * @param fields contains data for executing the method.
     * @return the {@link UserResponse} which updated password.
     * @throws ValidException if the data is entered incorrectly.
     * @throws NotFoundException if User is not exists by finding it by UUID.
     */
    @Override
    public UserResponse changingPassword(Map<String, Object> fields) {

        if (fields == null ||
            fields.isEmpty() ||
            fields.containsValue(null) ||
            fields.containsValue("") ||
            !fields.keySet().containsAll(Arrays.asList("login", "oldpassword", "newpassword"))) {
            throw ValidException.of();
        }

        String login = fields.get("login").toString();
        String oldpassword = fields.get("oldpassword").toString();
        String newpassword = fields.get("newpassword").toString();

        if (oldpassword.equals(newpassword)) {
            throw new InvalidDataException("Пароли совпадают, придумайте новый пароль!");
        }


        Optional<User> userInDB = userDao.findUserByLoginAndPassword(login, oldpassword);

        if (userInDB.isPresent()) {
            User user = userInDB.get();

            fields.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(User.class, k);

                if (field != null) {
                    field.setAccessible(true);

                    ReflectionUtils.setField(field, user, v);
                }
            });
            User updatedUser = userDao.update(user);
            UserResponse userResponse = userMapper.toResponse(updatedUser);
            log.info("Service method changingPassword: {}", userResponse);

            return userResponse;
        } else {
            throw NotFoundException.of(User.class, login);
        }
    }
}
