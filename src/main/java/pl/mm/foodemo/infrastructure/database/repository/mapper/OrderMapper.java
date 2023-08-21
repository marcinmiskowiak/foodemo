package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.infrastructure.database.entity.OrderEntity;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapper {

    private final AddressMapper addressMapper;
    private final RestaurantMapper restaurantMapper;
    private final UserMapper userMapper;
    private final MealOrderMapper mealOrderMapper;

    public Order map(OrderEntity entity) {

        return Order.builder()
                .orderId(entity.getOrderId())
                .orderNumber(entity.getOrderNumber())
                .status(entity.getStatus())
                .receivedDateTime(entity.getReceivedDateTime())
                .completedDateTime(entity.getCompletedDateTime())
                .restaurant(restaurantMapper.map(entity.getRestaurant()))
                .address(addressMapper.map(entity.getAddress()))
                .user(userMapper.map(entity.getUser()))
                .mealOrders(
                        entity.getMealOrders() == null ?
                                Collections.emptySet() : entity.getMealOrders().stream().map(mealOrderMapper::map).collect(Collectors.toSet()
                        )
                )
                .build();
    }

    public OrderEntity map(Order order) {
        return OrderEntity.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .receivedDateTime(order.getReceivedDateTime())
                .completedDateTime(order.getCompletedDateTime())
                .address(addressMapper.map(order.getAddress()))
                .restaurant(restaurantMapper.map(order.getRestaurant()))
                .user(userMapper.map(order.getUser()))
                .mealOrders(
                        order.getMealOrders() == null ?
                                Collections.emptySet() : order.getMealOrders().stream().map(mealOrderMapper::map).collect(Collectors.toSet()
                        )
                )
                .build();
    }
}
