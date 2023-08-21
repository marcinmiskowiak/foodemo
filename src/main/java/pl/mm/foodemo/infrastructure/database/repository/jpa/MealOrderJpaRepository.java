package pl.mm.foodemo.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.infrastructure.database.entity.MealOrderEntity;

import java.util.List;

@Repository
public interface MealOrderJpaRepository extends JpaRepository<MealOrderEntity, Long> {


    List<MealOrderEntity> findFirst3ByOrderByMealOrderIdDesc();

    List<MealOrderEntity> findAllByOrderOrderId(Long orderId);

    @Modifying
    @Query("DELETE FROM MealOrderEntity mo WHERE mo.order.orderId = :orderId")
    void deleteAllByOrderId(@Param("orderId") Long orderId);
}
