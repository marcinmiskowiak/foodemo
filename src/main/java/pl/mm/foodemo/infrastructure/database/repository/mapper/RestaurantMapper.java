package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.infrastructure.database.entity.RestaurantEntity;

import java.util.Objects;

@Component
@AllArgsConstructor
public class RestaurantMapper {

    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    public Restaurant map(RestaurantEntity entity) {
        if (Objects.isNull(entity)) {
            return Restaurant.builder().build();
        }

        return Restaurant.builder()
                .restaurantId(entity.getRestaurantId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .registrationDateTime(entity.getRegistrationDateTime())
                .address(addressMapper.map(entity.getAddress()))
                .owner(userMapper.map(entity.getOwner()))
                .build();
    }

    public RestaurantEntity map(Restaurant restaurant) {
        if (Objects.isNull(restaurant)) {
            return RestaurantEntity.builder().build();
        }

        return RestaurantEntity.builder()
                .restaurantId(restaurant.getRestaurantId())
                .name(restaurant.getName())
                .phone(restaurant.getPhone())
                .registrationDateTime(restaurant.getRegistrationDateTime())
                .address(addressMapper.map(restaurant.getAddress()))
                .owner(userMapper.map(restaurant.getOwner()))
                .build();
    }

}
