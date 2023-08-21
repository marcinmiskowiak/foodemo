package pl.mm.foodemo.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Address {

    Long addressId;
    String country;
    String city;
    String postalCode;
    String street;
    String buildingNumber;
    String apartmentNumber;


}