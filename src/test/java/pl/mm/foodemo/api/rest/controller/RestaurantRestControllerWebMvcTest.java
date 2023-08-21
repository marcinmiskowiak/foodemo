package pl.mm.foodemo.api.rest.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.mm.foodemo.api.rest.controller.RestaurantRestController.API_RESTAURANTS;

@WebMvcTest(controllers = RestaurantRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantRestControllerWebMvcTest {

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantMapperDTO restaurantMapper;

    @Test
    void shouldReturnListOfRestaurants() throws Exception {
        List<Restaurant> restaurantList = List.of(
                DomainFixtures.someRestaurant1(),
                DomainFixtures.someRestaurant2(),
                DomainFixtures.someRestaurant3()
        );

        Mockito.when(restaurantService.findAll()).thenReturn(restaurantList);
        Mockito.when(restaurantMapper.map(Mockito.any(Restaurant.class)))
                .thenReturn(DTOFixtures.someRestaurant1())
                .thenReturn(DTOFixtures.someRestaurant2())
                .thenReturn(DTOFixtures.someRestaurant3());

        mockMvc.perform(get(API_RESTAURANTS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurants").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurants.length()").value(3))
                .andExpect(jsonPath("$.restaurants[0].restaurantId").value(1))
                .andExpect(jsonPath("$.restaurants[0].name", equalTo("name1")))
                .andExpect(jsonPath("$.restaurants[1].restaurantId").value(2))
                .andExpect(jsonPath("$.restaurants[1].name", equalTo("name2")))
                .andExpect(jsonPath("$.restaurants[2].restaurantId").value(3))
                .andExpect(jsonPath("$.restaurants[2].name", equalTo("name3")));


    }

    @Test
    void shouldReturnEmptyListOfRestaurants() throws Exception {
        List<Restaurant> restaurantList = Collections.emptyList();

        Mockito.when(restaurantService.findAll()).thenReturn(restaurantList);

        mockMvc.perform(get(API_RESTAURANTS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurants").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurants.length()").value(0));


    }
}