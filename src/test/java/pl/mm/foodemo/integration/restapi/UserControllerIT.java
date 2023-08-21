package pl.mm.foodemo.integration.restapi;


import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.UserRegisterDTO;
import pl.mm.foodemo.configuration.RestAssuredIntegrationTestBase;
import pl.mm.foodemo.support.UserControllerSupport;
import pl.mm.foodemo.util.DTOFixtures;

import static org.assertj.core.api.Assertions.assertThat;


public class UserControllerIT extends RestAssuredIntegrationTestBase implements UserControllerSupport {

    @Test
    void thatUserCanBeRegisteredAndFoundCorrectly() {
        //given
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();


        //when
        Response response = registerUser(userRegisterDTO);

        String location = response.getHeader("Location");
        String userId = location.substring(location.lastIndexOf("/") + 1);

        //then
        UserDTO user = findUser(Long.valueOf(userId));


        assertThat(userId).isNotEmpty();
        assertThat(user.getEmail()).isEqualTo(userRegisterDTO.getEmail());
        assertThat(user.getPhone()).isEqualTo(userRegisterDTO.getPhone());
        assertThat(user.getSurname()).isEqualTo(userRegisterDTO.getSurname());

    }


    @Test
    void thatUserCanBeDeletedCorrectly() {
        //given
        UserRegisterDTO userRegisterDTO = DTOFixtures.someUserRegisterDTO1();

        //when
        Response response = registerUser(userRegisterDTO);

        String location = response.getHeader("Location");
        Long userId = Long.valueOf(location.substring(location.lastIndexOf("/") + 1));

        UserDTO user = findUser(userId);

        assertThat(user.getEmail()).isEqualTo(userRegisterDTO.getEmail());
        deleteUser(userId);

        int statusCode = findNonExistingUser(userId);

        //then
        assertThat(statusCode).isEqualTo(404);

    }

}
