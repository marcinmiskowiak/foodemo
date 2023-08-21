package pl.mm.foodemo.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.mm.foodemo.api.rest.controller.UserRestController.API_USERS;
import static pl.mm.foodemo.api.rest.controller.UserRestController.USER_ID;


@WebMvcTest(controllers = UserRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerWebMvcTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapperDTO userMapper;

    @Test
    void shouldRegisterUser() throws Exception {
        // Given
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();
        User user = DomainFixtures.someUser1();

        when(userService.registerUser(any(UserRegisterDTO.class))).thenReturn(user);

        // When/Then
        mockMvc.perform(post(API_USERS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", API_USERS + "/" + user.getUserId()));
    }

    @Test
    void shouldGetUser() throws Exception {
        // Given
        Long userId = 1L;
        UserDTO userDTO = DTOFixtures.someUserDTO1().withUserId(userId);
        User user = DomainFixtures.someUser1().withUserId(userId);

        when(userService.findUserById(userId)).thenReturn(user);
        when(userMapper.map(user)).thenReturn(userDTO);

        // When/Then
        mockMvc.perform(get(API_USERS + USER_ID, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.surname").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() throws Exception {
        // Given
        Long userId = 1L;

        when(userService.findUserById(userId)).thenReturn(null);

        // When/Then
        mockMvc.perform(get(API_USERS + USER_ID, userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        // Given
        Long userId = 1L;
        doNothing().when(userService).removeById(userId);

        // When/Then
        mockMvc.perform(delete("/api/users/{userId}", userId))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingUser() throws Exception {
        // Given
        Long userId = 1L;

        doThrow(NotFoundException.class).when(userService).removeById(userId);

        // When/Then
        mockMvc.perform(delete("/api/users/{userId}", userId))
                .andExpect(status().isNotFound());
    }

}