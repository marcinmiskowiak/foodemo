package pl.mm.foodemo.api.dto;


import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.mm.foodemo.domain.OrderStatus;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class OrderDTO {

    Long orderId;
    String orderNumber;
    OrderStatus status;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    RestaurantDTO restaurant;
    UserDTO user;
    Set<MealOrderDTO> mealOrders;


}