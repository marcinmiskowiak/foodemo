package pl.mm.foodemo.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mm.foodemo.business.dao.DeliveryAddressDAO;
import pl.mm.foodemo.domain.DeliveryAddress;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DeliveryAddressServiceTest {


    @Mock
    private DeliveryAddressDAO deliveryAddressDAO;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Test
    void shouldSaveDeliveryAddresses() {
        Long restaurantId = 1L;
        String postalCode = "12-345";
        String deliveryStreets = "Street1, Street2, Street3";

        Restaurant restaurant = DomainFixtures.someRestaurant1().withRestaurantId(restaurantId);

        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);

        deliveryAddressService.addDeliveryStreets(restaurantId, postalCode, deliveryStreets);

        Mockito.verify(restaurantService).findById(restaurantId);
        Mockito.verify(deliveryAddressDAO).saveAll(anyList());
    }

    @Test
    void shouldReturnDeliveryAddressesGroupedByPostalCode() {
        Restaurant restaurant = DomainFixtures.someRestaurant1();
        DeliveryAddress deliveryAddress1 = DomainFixtures.someDeliveryAddress();
        DeliveryAddress deliveryAddress2 = DomainFixtures.someDeliveryAddress2();
        DeliveryAddress deliveryAddress3 = DomainFixtures.someDeliveryAddress3();

        when(deliveryAddressDAO.getAllByRestaurant(restaurant))
                .thenReturn(
                        List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3)
                );

        Map<String, List<String>> result = deliveryAddressService.getAllByRestaurant(restaurant);

        assertEquals(2, result.size());
        assertEquals(List.of(deliveryAddress1.getStreet(), deliveryAddress2.getStreet()), result.get(deliveryAddress1.getPostalCode()));
        assertEquals(List.of(deliveryAddress3.getStreet()), result.get(deliveryAddress3.getPostalCode()));

    }

}