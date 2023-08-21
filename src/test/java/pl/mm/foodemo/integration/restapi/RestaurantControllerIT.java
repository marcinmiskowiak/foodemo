package pl.mm.foodemo.integration.restapi;


import org.junit.jupiter.api.Test;
import pl.mm.foodemo.api.dto.RestaurantsDTO;
import pl.mm.foodemo.configuration.RestAssuredIntegrationTestBase;
import pl.mm.foodemo.support.RestaurantControllerSupport;

import static org.assertj.core.api.Assertions.assertThat;


public class RestaurantControllerIT extends RestAssuredIntegrationTestBase implements RestaurantControllerSupport {

    @Test
    void thatRestaurantsCanBeShowedCorrectly() {

        RestaurantsDTO restaurantsDTO = listRestaurants();

        assertThat(restaurantsDTO.getRestaurants()).hasSizeGreaterThan(0);
    }

}
