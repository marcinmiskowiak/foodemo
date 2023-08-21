package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.infrastructure.database.entity.MealEntity;
@Component
@AllArgsConstructor
public class MealMapper {

    private final RestaurantMapper restaurantMapper;

    public Meal map(MealEntity entity) {
        return Meal.builder()
                .mealId(entity.getMealId())
                .name(entity.getName())
                .category(entity.getCategory())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageName(entity.getImageName())
                .build();
    }

    public MealEntity map(Meal meal) {
        return MealEntity.builder()
                .mealId(meal.getMealId())
                .name(meal.getName())
                .category(meal.getCategory())
                .description(meal.getDescription())
                .price(meal.getPrice())
                .imageName(meal.getImageName())
                .restaurant(restaurantMapper.map(meal.getRestaurant()))
                .build();
    }

}
