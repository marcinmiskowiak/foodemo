package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.DeliveryAddressDAO;
import pl.mm.foodemo.domain.DeliveryAddress;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.DeliveryAddressMapper;
import pl.mm.foodemo.infrastructure.database.repository.mapper.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository jpaRepository;

    private final DeliveryAddressMapper mapper;
    private final RestaurantMapper restaurantMapper;

    @Override
    public void saveAll(List<DeliveryAddress> deliveryAddresses) {
        jpaRepository.saveAll(
                deliveryAddresses.stream().map(mapper::map).collect(Collectors.toList())
        );
    }

    @Override
    public List<DeliveryAddress> getAllByRestaurant(Restaurant restaurant) {
        return jpaRepository.findAllByRestaurant(
                        restaurantMapper.map(restaurant)
                )
                .stream()
                .map(mapper::map)
                .toList();
    }
}
