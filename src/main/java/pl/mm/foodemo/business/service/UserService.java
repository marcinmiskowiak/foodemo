package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.business.dao.UserDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Role;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.domain.UserRoles;
import pl.mm.foodemo.domain.exception.UserRegistrationException;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private UserDAO repository;

    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();
        String phone = userRegisterDTO.getPhone();

        if (repository.findUserByEmail(email).isPresent()) {
            throw new UserRegistrationException("User with email: %s already exists".formatted(email));
        }

        if (repository.findUserByPhone(phone).isPresent()) {
            throw new UserRegistrationException("User with phone: %s already exists".formatted(phone));
        }


        Address address = Address.builder()
                .country(userRegisterDTO.getCountry())
                .buildingNumber(userRegisterDTO.getBuildingNumber())
                .city(userRegisterDTO.getCity())
                .postalCode(userRegisterDTO.getPostalCode())
                .apartmentNumber(userRegisterDTO.getApartmentNumber())
                .street(userRegisterDTO.getStreet())
                .build();


        User user = User.builder()
                .address(address)
                .phone(phone)
                .email(email)
                .surname(userRegisterDTO.getSurname())
                .name(userRegisterDTO.getName())
                .registrationDateTime(OffsetDateTime.now())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .roles(getRoles(userRegisterDTO.getIsRestaurantOwner()))
                .build();

        return repository.saveUser(user);

    }

    @Transactional
    public User findUserById(Long userId) {
        return repository.findUserById(userId);
    }

    @Transactional
    public User saveUser(User userUpdated) {
       return repository.saveUser(userUpdated);
    }

    @Transactional
    public void removeById(Long userId) {
        repository.removeById(userId);
    }


    private Set<Role> getRoles(Boolean isRestaurantOwner) {
        Set<Role> userRoles = new HashSet<>();

        userRoles.add(
                Role.builder()
                        .role(UserRoles.user.toString())
                        .build()
        );

        if (isRestaurantOwner) {
            userRoles.add(Role.builder().role(UserRoles.owner.toString()).build());
        }
        return userRoles;
    }
}
