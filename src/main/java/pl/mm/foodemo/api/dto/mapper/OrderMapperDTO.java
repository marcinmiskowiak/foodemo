package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.OrderDTO;
import pl.mm.foodemo.domain.Order;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderMapperDTO {

    RestaurantMapperDTO restaurantMapper;
    MealOrderMapperDTO mealOrderMapper;
    UserMapperDTO userMapper;

    public OrderDTO map(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .completedDateTime(order.getCompletedDateTime())
                .receivedDateTime(order.getReceivedDateTime())
                .restaurant(restaurantMapper.map(order.getRestaurant()))
                .status(order.getStatus())
                .user(userMapper.map(order.getUser()))
                .mealOrders(order.getMealOrders().stream().map(a -> mealOrderMapper.map(a)).collect(Collectors.toSet()))
                .build();
    }
}
