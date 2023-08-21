package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.mapper.OrderWithDetailsMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(RestaurantCompletedOrdersController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
class RestaurantCompletedOrdersControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private RestaurantMapperDTO restaurantMapper;

    @MockBean
    private OrderWithDetailsMapperDTO orderWithDetailsMapper;

    @Test
    void testRestaurantCompletedOrdersPage() throws Exception {
        // Given
        long restaurantId = 1L;
        Restaurant restaurant = DomainFixtures.someRestaurant1();
        RestaurantDTO restaurantDTO = DTOFixtures.someRestaurant1();
        Order order = DomainFixtures.someOrder();
        List<Order> orders = List.of(order);

        // When
        when(restaurantService.findById(anyLong())).thenReturn(DomainFixtures.someRestaurant1());
        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);
        when(orderService.getAllOrdersByRestaurantIdAndStatus(anyLong(), any())).thenReturn(orders);
        when(orderWithDetailsMapper.map(order)).thenReturn(DTOFixtures.someOrderWithDetails());

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/panel/restaurant/" + restaurantId + "/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owner_panel/restaurant_completed_orders"))
                .andExpect(model().attributeExists("restaurantDTO"))
                .andExpect(model().attributeExists("orderWithDetailsDTOs"));
    }
}
