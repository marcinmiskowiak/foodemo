package pl.mm.foodemo.support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.UserRegisterDTO;

import static pl.mm.foodemo.api.rest.controller.UserRestController.API_USERS;

public interface UserControllerSupport {

    RequestSpecification requestSpecification();

    default Response registerUser(final UserRegisterDTO userRegisterDTO) {
        return requestSpecification()
                .body(userRegisterDTO)
                .post(API_USERS)
                .thenReturn();
    }


    default UserDTO findUser(final Long userId) {
        return requestSpecification()
                .get(API_USERS + "/" + userId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(UserDTO.class);
    }

    default int findNonExistingUser(final Long userId) {
        return requestSpecification()
                .get(API_USERS + "/" + userId)
                .thenReturn()
                .statusCode();
    }

    default void deleteUser(final Long userId) {
        requestSpecification()
                .delete(API_USERS + "/" + userId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
