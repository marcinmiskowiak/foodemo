package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mm.foodemo.api.dto.AddingDeliveryStreetsDTO;
import pl.mm.foodemo.api.dto.PairDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.DeliveryAddressService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(EditRestaurantDeliveryStreetsController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
class EditRestaurantDeliveryStreetsControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private DeliveryAddressService deliveryAddressService;

    @MockBean
    private AddressService addressService;

    @Test
    void testEditDeliveryStreetsPage() throws Exception {
        // Given
        Long restaurantId = 1L;
        Restaurant restaurant = DomainFixtures.someRestaurant1();

        Map<String, List<String>> deliveryStreets = Map.of("00-000", List.of("A", "B", "C"));

        // When
        when(restaurantService.findById(anyLong())).thenReturn(restaurant);
        when(deliveryAddressService.getAllByRestaurant(any())).thenReturn(deliveryStreets);
        when(addressService.getCityByPostalCode(any())).thenReturn("City");

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/panel/restaurant/" + restaurantId + "/edit/streets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owner_panel/restaurant_edit_streets"))
                .andExpect(model().attribute("restaurantId", restaurant.getRestaurantId()))
                .andExpect(model().attribute("restaurantName", restaurant.getName()))
                .andExpect(model().attribute("addingDeliveryStreetsDTO", AddingDeliveryStreetsDTO.builder().build()))
                .andExpect(model().attribute("deliveryStreets", deliveryStreets.entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> new PairDTO<>(entry.getKey(), "City"),
                                Map.Entry::getValue
                        ))));
    }

    @Test
    void testAddDeliveryStreets() throws Exception {
        // Given
        Long restaurantId = 1L;
        AddingDeliveryStreetsDTO addingDeliveryStreetsDTO = DTOFixtures.someAddingDeliveryStreetsDTO();
        String referrer = "/panel/restaurant/" + restaurantId + "/edit/streets";

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/panel/restaurant/" + restaurantId + "/edit/streets/add")
                        .param("postalCode", addingDeliveryStreetsDTO.getPostalCode())
                        .param("streets", String.join(",", addingDeliveryStreetsDTO.getStreets()))
                        .header(HttpHeaders.REFERER, referrer))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(referrer));

        // Then
        verify(deliveryAddressService, times(1)).addDeliveryStreets(eq(restaurantId), eq(addingDeliveryStreetsDTO.getPostalCode()), eq(addingDeliveryStreetsDTO.getStreets()));
    }
}
