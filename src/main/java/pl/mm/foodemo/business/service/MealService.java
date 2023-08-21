package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.business.dao.MealDAO;
import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;

@AllArgsConstructor
@Service
public class MealService {


    private final MealDAO repository;

    @Transactional
    public Long countAll() {
        return repository.countAll();
    }

    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    public List<Meal> findAllByRestaurantId(Restaurant restaurant) {
        return repository.findAllByRestaurantId(restaurant);
    }

    public Meal findById(Long mealId) {
        return repository.findById(mealId);
    }
}
