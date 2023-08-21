package pl.mm.foodemo.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mm.foodemo.business.dao.RestaurantDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DomainFixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {


    @Mock
    private RestaurantDAO restaurantDAO;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private RestaurantService restaurantService;


    @Test
    void testUpdateAddress() {
        Long restaurantId = 1L;

        Address currentAddress = DomainFixtures.someAddress1().withAddressId(2L);
        Restaurant restaurant = DomainFixtures.someRestaurant1().withRestaurantId(restaurantId).withAddress(currentAddress);

        Address updatedAddress = DomainFixtures.someAddress2().withAddressId(currentAddress.getAddressId());

        when(restaurantDAO.findById(restaurantId)).thenReturn(restaurant);
        when(addressService.saveAddress(updatedAddress)).thenReturn(updatedAddress);


        Address result = restaurantService.updateAddress(restaurantId, updatedAddress);


        assertEquals(updatedAddress, result);
        Mockito.verify(restaurantDAO).findById(restaurantId);
        Mockito.verify(addressService).saveAddress(updatedAddress);
        assertEquals(updatedAddress, result);
    }


}