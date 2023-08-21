package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.MealOrderDAO;
import pl.mm.foodemo.domain.MealOrder;
import pl.mm.foodemo.infrastructure.database.entity.MealOrderEntity;
import pl.mm.foodemo.infrastructure.database.repository.jpa.MealOrderJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.MealOrderMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MealOrderRepository implements MealOrderDAO {

    private final MealOrderJpaRepository jpaRepository;

    private final MealOrderMapper mapper;

    public List<MealOrder> findThreeLastOrderedMeals() {
        return jpaRepository.findFirst3ByOrderByMealOrderIdDesc().stream().map(mapper::map).toList();
    }

    @Override
    public void save(MealOrder mealOrder) {
        jpaRepository.saveAndFlush(mapper.map(mealOrder));
    }

    @Override
    public void saveAll(Set<MealOrder> mealOrders) {
        Set<MealOrderEntity> collect = mealOrders.stream().map(mapper::map).collect(Collectors.toSet());
        jpaRepository.saveAllAndFlush(collect);

    }


    @Override
    public void deleteMealOrderByOrderId(Long id) {
        jpaRepository.deleteAllByOrderId(id);
    }


}
