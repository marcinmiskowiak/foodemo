package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.DeliveryAddress;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;

public interface DeliveryAddressDAO {
    void saveAll(List<DeliveryAddress> deliveryAddresses);

    List<DeliveryAddress> getAllByRestaurant(Restaurant restaurant);
}
