package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.AddressDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.infrastructure.database.entity.AddressEntity;
import pl.mm.foodemo.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.AddressMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {

    private final AddressJpaRepository jpaRepository;

    private final AddressMapper mapper;


    @Override
    public Address saveAddress(Address address) {
        AddressEntity addressToSave = mapper.map(address);
        AddressEntity addressSaved = jpaRepository.saveAndFlush(addressToSave);
        return mapper.map(addressSaved);
    }

    public Optional<Address> findAddressById(Long addressId) {
        return jpaRepository.findFirstByAddressId(addressId).map(mapper::map);

    }
}
