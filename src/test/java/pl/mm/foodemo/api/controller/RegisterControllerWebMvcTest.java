package pl.mm.foodemo.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.util.DTOFixtures;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
class RegisterControllerWebMvcTest {


    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterPage() throws Exception {
        mockMvc.perform(get(RegisterController.REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDTO"));
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();

        mockMvc.perform(post(RegisterController.REGISTER)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("userRegisterDTO", userRegisterDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));


    }

    @ParameterizedTest
    @MethodSource("provideUserRegisterDTOs")
    public void testRegistrationWithValidation(UserRegisterDTO userDTO, boolean isValid) throws Exception {
        ResultMatcher expectedStatus = isValid ? status().is3xxRedirection() : status().isBadRequest();
        ResultMatcher expectedView = isValid ? redirectedUrl("/login") : view().name("error");

        mockMvc.perform(post(RegisterController.REGISTER)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("userRegisterDTO", userDTO))
                .andExpect(expectedStatus)
                .andExpect(expectedView);
    }

    private static Stream<Arguments> provideUserRegisterDTOs() {
        return Stream.of(
                Arguments.of(DTOFixtures.someUserRegisterDTO1(), true),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withCountry(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withCity(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withBuildingNumber(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withStreet(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withPostalCode("00 000"), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withPostalCode(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withEmail(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withName(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withSurname(""), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withCountry(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withCity(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withBuildingNumber(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withApartmentNumber(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withStreet(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withName(DTOFixtures.someString), false),
                Arguments.of(DTOFixtures.someUserRegisterDTO1().withSurname(DTOFixtures.someString), false)
        );
    }

}