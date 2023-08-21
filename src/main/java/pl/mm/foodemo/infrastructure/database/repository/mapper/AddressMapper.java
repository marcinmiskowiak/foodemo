package pl.mm.foodemo.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.infrastructure.database.entity.AddressEntity;

@Component
@AllArgsConstructor
public class AddressMapper {

    public Address map(AddressEntity entity) {
        return Address.builder()
                .addressId(entity.getAddressId())
                .apartmentNumber(entity.getApartmentNumber())
                .street(entity.getStreet())
                .city(entity.getCity())
                .country(entity.getCountry())
                .buildingNumber(entity.getBuildingNumber())
                .postalCode(entity.getPostalCode())
                .build();

    }

    public AddressEntity map(Address address) {
        return AddressEntity.builder()
                .addressId(address.getAddressId())
                .apartmentNumber(address.getApartmentNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .buildingNumber(address.getBuildingNumber())
                .postalCode(address.getPostalCode())
                .build();
    }
}
