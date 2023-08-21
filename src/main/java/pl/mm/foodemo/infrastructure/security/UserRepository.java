package pl.mm.foodemo.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.UserDAO;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.database.repository.mapper.UserMapper;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository implements UserDAO {

    private UserJpaRepository repository;
    private UserMapper mapper;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmail(email).map(userEntity -> mapper.map(userEntity));
    }

    @Override
    public User saveUser(User user) {
        UserEntity userToSave = mapper.map(user).withActive(true);
        UserEntity userSaved = repository.saveAndFlush(userToSave);

        return mapper.map(userSaved);
    }

    @Override
    public User findUserById(Long userId) {
        return mapper.map(
                repository.findById(userId).orElseThrow(
                        () -> new NotFoundException("No user with id: %s found".formatted(userId))
                )
        );
    }

    @Override
    public Optional<User> findUserByPhone(String phone) {
        return repository.findByPhone(phone).map(userEntity -> mapper.map(userEntity));
    }

    @Override
    public void removeById(Long userId) {
        repository.deleteById(userId);
    }


}
