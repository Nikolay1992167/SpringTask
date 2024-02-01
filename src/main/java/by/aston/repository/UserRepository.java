package by.aston.repository;

import by.aston.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds one {@link User} entity by UUID.
     *
     * @param uuid the UUID of the User entity.
     * @return an {@link Optional} containing the User entity with the specified UUID, or an empty Optional if
     * no such entity exists in the database.
     */
    Optional<User> findByUuid(UUID uuid);

    /**
     * Finds one {@link User} entity by his login and password.
     *
     * @param login the login of User.
     * @param password the password of User.
     * @return an {@link Optional} containing the User entity with the specified login and password,
     * or an empty Optional if no such entity exists in the database.
     */
    Optional<User> findUserByLoginAndPassword(String login, String password);

    /**
     * Deletes one {@link User} entity by UUID.
     *
     * @param uuid the UUID of the User entity.
     */
    void deleteByUuid(UUID uuid);
}
