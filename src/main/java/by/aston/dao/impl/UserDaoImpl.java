package by.aston.dao.impl;

import by.aston.dao.UserDao;
import by.aston.entity.User;
import by.aston.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<User> findById(UUID id) {

        try (Session session = sessionFactory.openSession()) {

            User user = session.createQuery("SELECT u FROM User u WHERE u.uuid = :uuid", User.class)
                    .setParameter("uuid", id)
                    .uniqueResult();

            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAll() {

        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("SELECT u FROM User u", User.class);

            return query.getResultList();
        }
    }

    @Override
    public User save(User user) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();

            return user;
        }
    }

    @Override
    public User update(User user) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            User merged = session.merge(user);
            session.getTransaction().commit();

            return merged;
        }
    }

    @Override
    public void delete(UUID id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Optional<User> person = findById(id);
            session.remove(person.get());
            session.getTransaction().commit();
        }
    }
}
