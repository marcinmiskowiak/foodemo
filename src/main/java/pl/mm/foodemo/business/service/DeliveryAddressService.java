package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mm.foodemo.business.dao.DeliveryAddressDAO;
import pl.mm.foodemo.domain.DeliveryAddress;
import pl.mm.foodemo.domain.Restaurant;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DeliveryAddressService {

    private final DeliveryAddressDAO repository;
    private final RestaurantService restaurantService;

    public void addDeliveryStreets(Long restaurantId, String postalCode, String deliveryStreets) {

        Restaurant restaurant = restaurantService.findById(restaurantId);

        List<DeliveryAddress> deliveryAddresses = Arrays.stream(deliveryStreets.split(","))
                .map(
                        street -> DeliveryAddress.builder()
                                .street(street.trim())
                                .postalCode(postalCode)
                                .restaurant(restaurant)
                                .build()
                ).toList();

        repository.saveAll(deliveryAddresses);
    }

    public Map<String, List<String>> getAllByRestaurant(Restaurant restaurant) {

        List<DeliveryAddress> deliveryAddresses = repository.getAllByRestaurant(restaurant);

        var collect = deliveryAddresses.stream().collect(
                Collectors.groupingBy(
                        DeliveryAddress::getPostalCode,
                        Collectors.mapping(DeliveryAddress::getStreet, Collectors.toList())
                )
        );

        return collect;
    }
}
