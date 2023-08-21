package pl.mm.foodemo.api.dto;


import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class DeliveryAddressDTO {

     Long deliveryAddressId;
     String street;

     String postalCode;
     RestaurantDTO restaurant;
}