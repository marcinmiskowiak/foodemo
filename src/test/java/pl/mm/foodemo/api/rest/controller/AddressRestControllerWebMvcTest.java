package pl.mm.foodemo.api.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.mm.foodemo.api.rest.controller.AddressRestController.ADDRESS_ID;
import static pl.mm.foodemo.api.rest.controller.AddressRestController.API_ADDRESSES;
import static pl.mm.foodemo.util.DTOFixtures.someString;

@WebMvcTest(controllers = AddressRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class AddressRestControllerWebMvcTest {


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @MockBean
    private AddressMapperDTO addressMapper;


    @Test
    void shouldUpdateAddressCorrectly() throws Exception {
        // Given
        Long addressId = 1L;
        AddressDTO addressDTO = DTOFixtures.someAddress1();
        Address address = DomainFixtures.someAddress1();


        when(addressService.findAddressById(addressId)).thenReturn(address.withAddressId(addressId));
        when(addressMapper.map(addressDTO)).thenReturn(address);
        when(addressService.saveAddress(address)).thenReturn(address);

        // When/Then
        mockMvc.perform(put(API_ADDRESSES + ADDRESS_ID, addressId).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(addressDTO))).andExpect(status().isOk()
        );

    }

    @Test
    void shouldReturnNotFoundWhenAddressNotFound() throws Exception {
        // Given
        Long addressId = 1L;
        AddressDTO addressDTO = DTOFixtures.someAddress1();


        when(addressService.findAddressById(addressId)).thenReturn(null);

        // When/Then
        mockMvc.perform(put(API_ADDRESSES + ADDRESS_ID, addressId).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(addressDTO))).andExpect(status().isNotFound()
        );

    }

    @ParameterizedTest
    @MethodSource("provideAddressDTOs")
    void shouldValidateAddress(AddressDTO addressDTO, Boolean correctAddress) throws Exception {
        // Given
        Long addressId = 1L;
        Address address = DomainFixtures.someAddress1();


        when(addressService.findAddressById(addressId)).thenReturn(address.withAddressId(addressId));
        when(addressMapper.map(addressDTO)).thenReturn(address);
        when(addressService.saveAddress(address)).thenReturn(address);

        // When/Then
        mockMvc.perform(put(API_ADDRESSES + ADDRESS_ID, addressId).contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(addressDTO))).andExpect(correctAddress ? status().isOk() : status().isBadRequest()
        );
    }

    private static Stream<Arguments> provideAddressDTOs() {

        return Stream.of(
                Arguments.of(DTOFixtures.someAddress1(), true),
                Arguments.of(DTOFixtures.someAddress1().withPostalCode("00 000"), false),
                Arguments.of(DTOFixtures.someAddress1().withStreet(""), false),
                Arguments.of(DTOFixtures.someAddress1().withCountry(""), false),
                Arguments.of(DTOFixtures.someAddress1().withBuildingNumber(""), false),
                Arguments.of(DTOFixtures.someAddress1().withCity(""), false),
                Arguments.of(DTOFixtures.someAddress1().withStreet(someString), false),
                Arguments.of(DTOFixtures.someAddress1().withCountry(someString), false),
                Arguments.of(DTOFixtures.someAddress1().withBuildingNumber(someString), false),
                Arguments.of(DTOFixtures.someAddress1().withCity(someString), false),
                Arguments.of(DTOFixtures.someAddress1().withApartmentNumber(someString), false)
        );
    }


}