package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class DeliveryAddress {

     Long deliveryAddressId;
     String street;
     String postalCode;
     Restaurant restaurant;
}