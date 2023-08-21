package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mm.foodemo.api.dto.OrderWithDetailsDTO;
import pl.mm.foodemo.api.dto.mapper.OrderWithDetailsMapperDTO;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(OrdersController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrdersControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderWithDetailsMapperDTO orderWithDetailsMapper;

    @Test
    @WithUserDetails()
    void testOrdersPage() throws Exception {
        // Given
        UserDetails userDetails = new CustomUserDetails(DomainFixtures.someUser2(), DomainFixtures.userAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);

        Order order = DomainFixtures.someOrder();
        OrderWithDetailsDTO orderWithDetailsDTO = DTOFixtures.someOrderWithDetails();

        List<Order> orders = List.of(order);

        // When
        Mockito.when(orderService.getAllOrdersByUserId(any())).thenReturn(orders);
        Mockito.when(orderWithDetailsMapper.map(order)).thenReturn(orderWithDetailsDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user_panel/orders"))
                .andExpect(model().attributeExists("orderWithDetailsDTOs"));
    }
}
