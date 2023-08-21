package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(RestaurantsPanelController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc(addFilters = false)
class RestaurantsPanelControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserService userService;

    @MockBean
    private AddressMapperDTO addressMapper;

    @MockBean
    private UserMapperDTO userMapper;

    @MockBean
    private RestaurantMapperDTO restaurantMapper;

    @Test
    @WithUserDetails()
    void testOwnerPanelPage() throws Exception {
        // Given
        User user = DomainFixtures.someUser1();
        UserDetails userDetails = new CustomUserDetails(user, DomainFixtures.userAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);

        AddressDTO addressDTO = DTOFixtures.someAddress1();
        Address address = DomainFixtures.someAddress1();
        UserDTO userDTO = DTOFixtures.someUserDTO1();
        Restaurant restaurant = DomainFixtures.someRestaurant1();
        RestaurantDTO restaurantDTO = DTOFixtures.someRestaurant1();

        // When
        when(addressMapper.map(address)).thenReturn(addressDTO);
        when(userService.findUserById(user.getUserId())).thenReturn(user);
        when(userMapper.map(user)).thenReturn(userDTO);
        when(restaurantService.findByOwnerId(user.getUserId())).thenReturn(List.of(restaurant));
        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/panel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owner_panel/panel"))
                .andExpect(model().attributeExists("addressDTO"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attributeExists("restaurantDTOs"));
    }
}
