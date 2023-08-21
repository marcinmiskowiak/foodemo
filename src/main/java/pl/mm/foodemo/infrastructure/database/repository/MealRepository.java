package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.MealDAO;
import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.database.repository.jpa.MealJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.MealMapper;
import pl.mm.foodemo.infrastructure.database.repository.mapper.RestaurantMapper;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MealRepository implements MealDAO {

    private final MealJpaRepository jpaRepository;

    private final RestaurantMapper restaurantMapper;
    private final MealMapper mapper;

    @Override
    public Long countAll() {
        return jpaRepository.count();
    }

    @Override
    public Meal save(Meal meal) {
        return mapper.map(jpaRepository.saveAndFlush(mapper.map(meal)));
    }

    @Override
    public List<Meal> findAllByRestaurantId(Restaurant restaurant) {
        return jpaRepository.findAllByRestaurant(restaurantMapper.map(restaurant)).stream().map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public Meal findById(Long mealId) {
        return mapper.map(jpaRepository.findById(mealId).orElseThrow(
                () -> new NotFoundException("No meal with: %s found".formatted(mealId))
        ));
    }


}
