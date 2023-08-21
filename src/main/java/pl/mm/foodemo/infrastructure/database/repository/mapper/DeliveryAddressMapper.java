package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.DeliveryAddress;
import pl.mm.foodemo.infrastructure.database.entity.DeliveryAddressEntity;

@Component
@AllArgsConstructor
public class DeliveryAddressMapper {

    private final RestaurantMapper restaurantMapper;

    public DeliveryAddress map(DeliveryAddressEntity entity) {
        return DeliveryAddress.builder()
                .deliveryAddressId(entity.getDeliveryAddressId())
                .street(entity.getStreet())
                .postalCode(entity.getPostalCode())
                .restaurant(restaurantMapper.map(entity.getRestaurant()))
                .build();
    }

    public DeliveryAddressEntity map(DeliveryAddress deliveryAddress) {
        return DeliveryAddressEntity.builder()
                .deliveryAddressId(deliveryAddress.getDeliveryAddressId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .restaurant(restaurantMapper.map(deliveryAddress.getRestaurant()))
                .build();
    }

}
