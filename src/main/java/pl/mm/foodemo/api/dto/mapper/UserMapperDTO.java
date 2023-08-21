package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.domain.Role;
import pl.mm.foodemo.domain.User;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapperDTO {


    private final AddressMapperDTO addressMapper;

    public User map(UserDTO user) {

        return User.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(
                        user.getAddress()
                )
                .surname(user.getSurname())
                .name(user.getName())
                .registrationDateTime(user.getRegistrationDateTime())
                .roles(
                        user.getRoles().stream().map(
                                roleEntity -> Role.builder().roleId(roleEntity.getRoleId()).role(roleEntity.getRole()).build()).collect(Collectors.toSet()
                        )
                )
                .password(user.getPassword())
                .active(user.getActive())
                .build();

    }

    public UserDTO map(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(
                        user.getAddress()
                )
                .surname(user.getSurname())
                .name(user.getName())
                .registrationDateTime(user.getRegistrationDateTime())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(
                        role-> Role.builder().roleId(role.getRoleId()).role(role.getRole()).build()
                ).collect(Collectors.toSet()))
                .active(user.getActive())
                .build();

    }


}

