package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.business.dao.AddressDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.postalCodes.api.DefaultApi;

import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class AddressService {


    private AddressDAO repository;

    @Transactional
    public Address saveAddress(Address address) {
        return repository.saveAddress(address);
    }

    public Address findAddressById(Long addressId) {
        Optional<Address> addressById = repository.findAddressById(addressId);

        if(addressById.isEmpty()) {
            throw new NotFoundException("Address with id: %s not found".formatted(addressId));
        }

        return addressById.get();
    }

    public String getCityByPostalCode(String postalCode) {

        DefaultApi defaultApi = new DefaultApi();

        try {
            return Objects.requireNonNull(defaultApi.zipCodeInfo(postalCode.trim(), "application/json", "").blockFirst()).getMiejscowosc();
        } catch (Exception e) {
            log.error("Postal Codes API Exception", e);
            return "";
        }
    }
}
