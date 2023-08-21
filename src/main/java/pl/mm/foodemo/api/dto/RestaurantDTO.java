package pl.mm.foodemo.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@AllArgsConstructor
public class RestaurantDTO {
    Long restaurantId;
    String name;
    String phone;
    OffsetDateTime registrationDateTime;
    AddressDTO address;
    Set<DeliveryAddressDTO> deliveryAddresses;
    Set<MealDTO> meals;
    Set<OrderDTO> orders;

}