package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findUserByEmail(String email);

    User saveUser(User user);

    User findUserById(Long userId);

    Optional<User> findUserByPhone(String phone);

    void removeById(Long userId);
}
