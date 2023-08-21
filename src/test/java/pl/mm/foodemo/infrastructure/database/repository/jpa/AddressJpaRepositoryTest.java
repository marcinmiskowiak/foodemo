package pl.mm.foodemo.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.mm.foodemo.configuration.PersistenceContainerTestConfiguration;
import pl.mm.foodemo.infrastructure.database.entity.AddressEntity;
import pl.mm.foodemo.util.EntityFixtures;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AddressJpaRepositoryTest {

    private AddressJpaRepository addressJpaRepository;

    @Test
    void thatAddressCanBeSavedCorrectly() {
        AddressEntity address = EntityFixtures.someAddressEntity1();

        long sizeBefore = addressJpaRepository.count();

        AddressEntity addressEntity = addressJpaRepository.saveAndFlush(address);
        long sizeAfter = addressJpaRepository.count();

        AddressEntity firstByAddressId = addressJpaRepository.findFirstByAddressId(addressEntity.getAddressId()).orElseThrow();

        assertEquals(sizeBefore + 1, sizeAfter);
        assertEquals(addressEntity, firstByAddressId);
    }

}