package com.nix.zhylina.services;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.entitie.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A class that represents the user operations services that are in the database.
 * This class includes the implementation of class methods {@link com.nix.zhylina.dao.impl.HibernateUserDao}
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 22.02.2022
 */
public class UserDaoService implements UserDao {
    private static final Logger LOG = LogManager.getLogger(UserDaoService.class);

    private final UserDao userDao;

    public UserDaoService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void remove(User user) {
        userDao.remove(user);
    }

    public boolean isUserDataValid(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                LOG.error(violation.getMessage());
            }
            return false;
        }
        return true;
    }

    public boolean isUserExists(String login, String password) {
        Optional<User> user = Optional.ofNullable(userDao.findByLogin(login));
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}
