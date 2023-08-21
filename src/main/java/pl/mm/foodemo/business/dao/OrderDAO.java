package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.domain.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderDAO {



    Long countAll();

    Order saveOrder(Order order);

    void setOrderStatusById(Long id, OrderStatus orderStatus);

    void setOrderCompletedDateTimeById(Long id, OffsetDateTime offsetDateTime);

    List<Order> findAllByUserId(Long userId);

    void deleteOrderById(Long id);

    List<Order> findAllByRestaurantIdAndStatus(Long id, OrderStatus orderStatus);
}
