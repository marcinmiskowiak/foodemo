package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.business.dao.MealOrderDAO;
import pl.mm.foodemo.domain.MealOrder;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MealOrderService {

    private final MealOrderDAO repository;
    @Transactional
    public List<MealOrder> findThreeLastOrderedMeals() {
        return repository.findThreeLastOrderedMeals();
    }

    @Transactional
    public void saveAll(Set<MealOrder> mealOrders) {
        repository.saveAll(mealOrders);
    }

    @Transactional
    public void deleteMealOrderByOrderId(Long id) {
        repository.deleteMealOrderByOrderId(id);
    }
}
