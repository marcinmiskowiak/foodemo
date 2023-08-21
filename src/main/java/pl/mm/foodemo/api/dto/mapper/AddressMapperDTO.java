package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.domain.Address;

@Component
@AllArgsConstructor
public class AddressMapperDTO {

    public AddressDTO map(Address address) {
        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .street(address.getStreet())
                .addressId(address.getAddressId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .buildingNumber(address.getBuildingNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }

    public Address map(AddressDTO address) {
        return Address.builder()
                .addressId(address.getAddressId())
                .street(address.getStreet())
                .addressId(address.getAddressId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .postalCode(address.getPostalCode())
                .buildingNumber(address.getBuildingNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }

}
