package pl.mm.foodemo.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.infrastructure.database.entity.MealEntity;
import pl.mm.foodemo.infrastructure.database.entity.RestaurantEntity;

import java.util.List;

@Repository
public interface MealJpaRepository extends JpaRepository<MealEntity, Long> {

    List<MealEntity> findAllByRestaurant(RestaurantEntity restaurant);

    long count();
}
