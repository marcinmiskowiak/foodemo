package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.MealOrder;

import java.util.List;
import java.util.Set;

public interface MealOrderDAO {


    List<MealOrder> findThreeLastOrderedMeals();

    void save(MealOrder mealOrder);

    void saveAll(Set<MealOrder> mealOrders);


    void deleteMealOrderByOrderId(Long id);
}
