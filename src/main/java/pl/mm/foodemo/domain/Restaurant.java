package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class Restaurant {
    Long restaurantId;
    String name;
    String phone;
    OffsetDateTime registrationDateTime;
    User owner;
    Address address;
    Set<DeliveryAddress> deliveryAddresses;
    Set<Meal> meals;
    Set<Order> orders;

}