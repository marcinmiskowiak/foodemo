package pl.mm.foodemo.api.rest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.domain.Address;

@RestController
@RequestMapping(AddressRestController.API_ADDRESSES)
@AllArgsConstructor
@Validated

public class AddressRestController {

    public static final String API_ADDRESSES = "/api/addresses";
    public static final String ADDRESS_ID = "/{addressId}";

    private final AddressService addressService;
    private final AddressMapperDTO addressMapper;

    @PutMapping(ADDRESS_ID)
    public ResponseEntity<?> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressDTO addressDTO
    ) {

        try {
            Address address = addressService.findAddressById(addressId);

            Address newAddress = addressMapper.map(addressDTO).withAddressId(address.getAddressId());

            addressService.saveAddress(newAddress);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.notFound().build();
        }
    }

}

