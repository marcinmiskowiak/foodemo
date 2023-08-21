package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.MealOrder;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.infrastructure.database.entity.MealOrderEntity;
import pl.mm.foodemo.infrastructure.database.entity.OrderEntity;

@Component
@AllArgsConstructor
public class MealOrderMapper {

    private MealMapper mealMapper;
    private AddressMapper addressMapper;
    private UserMapper userMapper;
    private RestaurantMapper restaurantMapper;

    public MealOrder map(MealOrderEntity entity) {
        return MealOrder.builder()
                .mealOrderId(entity.getMealOrderId())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .meal(
                        mealMapper.map(entity.getMeal())
                )
                .order(
                        Order.builder()
                                .orderId(entity.getOrder().getOrderId())
                                .address(addressMapper.map(entity.getOrder().getAddress()))
                                .user(userMapper.map(entity.getOrder().getUser()))
                                .orderNumber(entity.getOrder().getOrderNumber())
                                .receivedDateTime(entity.getOrder().getReceivedDateTime())
                                .completedDateTime(entity.getOrder().getCompletedDateTime())
                                .status(entity.getOrder().getStatus())
                                .restaurant(restaurantMapper.map(entity.getOrder().getRestaurant()))
                                .build()
                )
                .build();
    }

    public MealOrderEntity map(MealOrder mealOrder) {
        return MealOrderEntity.builder()
                .mealOrderId(mealOrder.getMealOrderId())
                .quantity(mealOrder.getQuantity())
                .price(mealOrder.getPrice())
                .meal(mealMapper.map(mealOrder.getMeal()))
                .order(
                        OrderEntity.builder()
                                .orderId(mealOrder.getOrder().getOrderId())
                                .address(addressMapper.map(mealOrder.getOrder().getAddress()))
                                .user(userMapper.map(mealOrder.getOrder().getUser()))
                                .orderNumber(mealOrder.getOrder().getOrderNumber())
                                .receivedDateTime(mealOrder.getOrder().getReceivedDateTime())
                                .completedDateTime(mealOrder.getOrder().getCompletedDateTime())
                                .status(mealOrder.getOrder().getStatus())
                                .restaurant(restaurantMapper.map(mealOrder.getOrder().getRestaurant()))
                                .build()
                )
                .build();
    }
}
