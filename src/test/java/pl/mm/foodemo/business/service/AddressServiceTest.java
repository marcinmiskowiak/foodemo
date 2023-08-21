package pl.mm.foodemo.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mm.foodemo.business.dao.AddressDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.postalCodes.api.DefaultApi;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressDAO addressDAO;

    @Mock
    private DefaultApi defaultApi;

    @InjectMocks
    private AddressService addressService;

    @Test
    void shouldCallRepositorySaveMethod() {
        Address address = DomainFixtures.someAddress1();
        when(addressDAO.saveAddress(address)).thenReturn(address);

        Address savedAddress = addressService.saveAddress(address);

        verify(addressDAO, times(1)).saveAddress(address);
        assertEquals(address, savedAddress);
    }

    @Test
    void shouldReturnFoundAddress() {
        Long addressId = 1L;
        Address expectedAddress = DomainFixtures.someAddress1();
        when(addressDAO.findAddressById(addressId)).thenReturn(Optional.of(expectedAddress));

        Address foundAddress = addressService.findAddressById(addressId);

        verify(addressDAO, times(1)).findAddressById(addressId);
        assertEquals(expectedAddress, foundAddress);
    }

    @Test
    void shouldThrowWhenAddressNotFound() {
        Long addressId = 1L;
        when(addressDAO.findAddressById(addressId)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> addressService.findAddressById(addressId));
        assertEquals("Address with id: %s not found".formatted(addressId), notFoundException.getMessage());
        verify(addressDAO, times(1)).findAddressById(addressId);
    }


}