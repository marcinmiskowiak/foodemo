package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.mapper.MealMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private final MealService mealService;

    @MockBean
    private final RestaurantMapperDTO restaurantMapper;

    @MockBean
    private final MealMapperDTO mealMapper;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void testUserRestaurantPage() throws Exception {
        //given
        Long restaurantId = 1L;
        Restaurant restaurant = DomainFixtures.someRestaurant4();
        RestaurantDTO restaurantDTO = DTOFixtures.someRestaurant4();

        //when
        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);
        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);
        when(mealService.findAllByRestaurantId(restaurant)).thenReturn(List.of());


        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/restaurant/{id}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(view().name("user_panel/restaurant"))
                .andExpect(model().attributeExists("restaurantDTO"))
                .andExpect(model().attributeExists("restaurantMealsByCategories"));

        verify(restaurantService, times(1)).findById(restaurantId);
        verify(restaurantMapper, times(1)).map(restaurant);
        verify(mealService, times(1)).findAllByRestaurantId(restaurant);
    }
}