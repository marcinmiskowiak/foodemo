package pl.mm.foodemo.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.mm.foodemo.api.dto.mapper.MealOrderMapperDTO;
import pl.mm.foodemo.business.service.MealOrderService;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.business.service.RestaurantService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
class HomeControllerWebMvcTest {


    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private MealService mealService;

    @MockBean
    private MealOrderService mealOrderService;

    @MockBean
    private MealOrderMapperDTO mealOrderMapperDTO;


    @Test
    public void testHomePageModel() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists(
                        "allRestaurantsSize",
                        "allOrdersSize",
                        "allMealsSize",
                        "lastThreeOrderedMealsDTOs",
                        "newestRestaurantsNames"
                ));

    }

}