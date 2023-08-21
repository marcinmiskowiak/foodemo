package pl.mm.foodemo.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.mm.foodemo.api.dto.AddressDTO;

import static pl.mm.foodemo.api.rest.controller.AddressRestController.API_ADDRESSES;

public interface AddressControllerSupport {

    RequestSpecification requestSpecification();

    default void addAddress(final AddressDTO addressDTO, long addressId) {
        requestSpecification()
                .body(addressDTO)
                .put(API_ADDRESSES + "/" + addressId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
