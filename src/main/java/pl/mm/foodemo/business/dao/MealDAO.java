package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;

public interface MealDAO {

    Long countAll();

    Meal save(Meal meal);

    List<Meal> findAllByRestaurantId(Restaurant restaurant);

    Meal findById(Long mealId);
}
