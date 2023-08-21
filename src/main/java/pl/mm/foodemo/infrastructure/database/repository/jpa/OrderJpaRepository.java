package pl.mm.foodemo.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.domain.OrderStatus;
import pl.mm.foodemo.infrastructure.database.entity.OrderEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    long count();

    @Modifying
    @Query("UPDATE OrderEntity o SET o.status = :status WHERE o.orderId = :id")
    void setOrderStatusById(@Param("id") Long id, @Param("status") OrderStatus status);

    @Modifying
    @Query("UPDATE OrderEntity o SET o.completedDateTime = :dateTime WHERE o.orderId = :id")
    void setOrderCompletedDateTimeById(@Param("id") Long id, @Param("dateTime") OffsetDateTime dateTime);


    List<OrderEntity> findAllByUserUserId(Long userId);

    @Query("""
            SELECT o FROM OrderEntity o 
            WHERE o.restaurant.restaurantId = :restaurantId  
            AND o.status = :status
            """)
    List<OrderEntity> findAllByRestaurantIdAndStatus(@Param("restaurantId") Long id, @Param("status") OrderStatus orderStatus);

}
