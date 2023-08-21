package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.domain.Restaurant;

@AllArgsConstructor
@Component
public class RestaurantMapperDTO {

    private final AddressMapperDTO addressMapper;


    public Restaurant map(RestaurantDTO restaurant) {
        return Restaurant.builder()
                .restaurantId(restaurant.getRestaurantId())
                .address(addressMapper.map(restaurant.getAddress()))
                .phone(restaurant.getPhone())
                .name(restaurant.getName())
                .registrationDateTime(restaurant.getRegistrationDateTime())
                .build();
    }
    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .restaurantId(restaurant.getRestaurantId())
                .address(addressMapper.map(restaurant.getAddress()))
                .phone(restaurant.getPhone())
                .name(restaurant.getName())
                .registrationDateTime(restaurant.getRegistrationDateTime())
                .build();
    }
}
