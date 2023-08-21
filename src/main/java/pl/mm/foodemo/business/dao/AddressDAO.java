package pl.mm.foodemo.business.dao;

import pl.mm.foodemo.domain.Address;

import java.util.Optional;

public interface AddressDAO {

    Address saveAddress(Address address);

    Optional<Address> findAddressById(Long addressId);
}
