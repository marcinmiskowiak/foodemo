package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.DeliveryAddressDTO;
import pl.mm.foodemo.domain.DeliveryAddress;

@AllArgsConstructor
@Component
public class DeliveryAddressMapperDTO {
    public DeliveryAddress map(DeliveryAddressDTO deliveryAddress) {
        return DeliveryAddress.builder()
                .deliveryAddressId(deliveryAddress.getDeliveryAddressId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .build();

    }
    public DeliveryAddressDTO map(DeliveryAddress deliveryAddress) {
        return DeliveryAddressDTO.builder()
                .deliveryAddressId(deliveryAddress.getDeliveryAddressId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .build();

    }
}
