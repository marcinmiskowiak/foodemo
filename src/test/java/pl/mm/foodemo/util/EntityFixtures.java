package pl.mm.foodemo.util;

import pl.mm.foodemo.infrastructure.database.entity.AddressEntity;

public class EntityFixtures {

    public static AddressEntity someAddressEntity1() {
        return AddressEntity.builder()
                .addressId(20L)
                .country("Poland")
                .city("Warsaw")
                .apartmentNumber("11")
                .postalCode("00-001")
                .street("SomeStreet")
                .buildingNumber("10")
                .build();
    }

}
