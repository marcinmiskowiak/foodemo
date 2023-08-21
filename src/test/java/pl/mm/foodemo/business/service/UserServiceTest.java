package pl.mm.foodemo.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.business.dao.UserDAO;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.domain.exception.UserRegistrationException;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserDAO userDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldRegisterUserSuccessfully() {
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();
        User userToSave = DomainFixtures.someUser1();

        when(userDAO.findUserByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.empty());
        when(userDAO.findUserByPhone(userRegisterDTO.getPhone())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("hashedPassword");
        when(userDAO.saveUser(any(User.class))).thenReturn(userToSave);

        User result = userService.registerUser(userRegisterDTO);

        assertEquals(userToSave, result);
        assertNotNull(result.getRegistrationDateTime());
        verify(userDAO).findUserByEmail(userRegisterDTO.getEmail());
        verify(userDAO).findUserByPhone(userRegisterDTO.getPhone());
        verify(userDAO).saveUser(any(User.class));
    }

    @Test
    void shouldThrowExceptionForDuplicateEmail() {
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();

        when(userDAO.findUserByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.of(User.builder().build()));

        UserRegistrationException userRegistrationException = assertThrows(UserRegistrationException.class, () -> userService.registerUser(userRegisterDTO));
         assertEquals("User with email: %s already exists".formatted(userRegisterDTO.getEmail()), userRegistrationException.getMessage());
        verify(userDAO).findUserByEmail(userRegisterDTO.getEmail());
        verify(userDAO, never()).findUserByPhone(any());
        verify(userDAO, never()).saveUser(any(User.class));
    }


    @Test
    void  shouldThrowExceptionForDuplicatePhone() {
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();

        when(userDAO.findUserByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.empty());
        when(userDAO.findUserByPhone(userRegisterDTO.getPhone())).thenReturn(Optional.of(User.builder().build()));

        UserRegistrationException userRegistrationException = assertThrows(UserRegistrationException.class, () -> userService.registerUser(userRegisterDTO));
         assertEquals("User with phone: %s already exists".formatted(userRegisterDTO.getPhone()), userRegistrationException.getMessage());
        verify(userDAO).findUserByEmail(userRegisterDTO.getEmail());
        verify(userDAO).findUserByEmail(any());
        verify(userDAO).findUserByPhone(any());
        verify(userDAO, never()).saveUser(any(User.class));
    }

}