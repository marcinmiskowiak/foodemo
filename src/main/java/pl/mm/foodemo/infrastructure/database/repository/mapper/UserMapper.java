package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.Role;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.infrastructure.security.RoleEntity;
import pl.mm.foodemo.infrastructure.security.UserEntity;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User map(UserEntity entity) {

        return User.builder()
                .userId(entity.getUserId())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(
                        addressMapper.map(entity.getAddress())
                )
                .surname(entity.getSurname())
                .name(entity.getName())
                .registrationDateTime(entity.getRegistrationDateTime())
                .roles(
                        entity.getRoles().stream().map(
                                roleEntity -> Role.builder().roleId(roleEntity.getRoleId()).role(roleEntity.getRole()).build()).collect(Collectors.toSet()
                        )
                )
                .password(entity.getPassword())
                .active(entity.getActive())
                .build();

    }

    public UserEntity map(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(
                        addressMapper.map(user.getAddress())
                )
                .surname(user.getSurname())
                .name(user.getName())
                .registrationDateTime(user.getRegistrationDateTime())
                .password(user.getPassword())
                .roles(
                        user.getRoles().stream().map(role -> RoleEntity.builder().role(role.getRole()).build()).collect(Collectors.toSet())
                )
                .password(user.getPassword())
                .active(user.getActive())
                .build();

    }


}
