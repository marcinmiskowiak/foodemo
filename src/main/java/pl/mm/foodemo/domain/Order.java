package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;


@With
@Value
@Builder
public class Order {

    Long orderId;
    String orderNumber;
    OrderStatus status;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    Restaurant restaurant;
    User user;
    Address address;
    Set<MealOrder> mealOrders;


}