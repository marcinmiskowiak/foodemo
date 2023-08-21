package pl.mm.foodemo.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.mm.foodemo.api.dto.RestaurantsDTO;

import static pl.mm.foodemo.api.rest.controller.RestaurantRestController.API_RESTAURANTS;

public interface RestaurantControllerSupport {

    RequestSpecification requestSpecification();

    default RestaurantsDTO listRestaurants() {
        return requestSpecification()
                .get(API_RESTAURANTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(RestaurantsDTO.class);
    }
}
