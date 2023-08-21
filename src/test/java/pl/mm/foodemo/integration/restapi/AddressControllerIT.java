package pl.mm.foodemo.integration.restapi;


import org.junit.jupiter.api.Test;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.configuration.RestAssuredIntegrationTestBase;
import pl.mm.foodemo.support.AddressControllerSupport;
import pl.mm.foodemo.util.DTOFixtures;


public class AddressControllerIT extends RestAssuredIntegrationTestBase implements AddressControllerSupport {

    @Test
    void thatAddressCanBeUpdatedCorrectly() {

        AddressDTO addressDTO = DTOFixtures.someAddress1();

        addAddress(addressDTO, 1);
    }

}
