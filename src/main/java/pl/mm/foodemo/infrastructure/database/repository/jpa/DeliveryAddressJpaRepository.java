package pl.mm.foodemo.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.infrastructure.database.entity.DeliveryAddressEntity;
import pl.mm.foodemo.infrastructure.database.entity.RestaurantEntity;

import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity, Long> {

    List<DeliveryAddressEntity> findAllByRestaurant(RestaurantEntity restaurant);

}
