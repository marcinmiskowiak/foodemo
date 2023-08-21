package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.User;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;
import pl.mm.foodemo.util.DTOFixtures;
import pl.mm.foodemo.util.DomainFixtures;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(RestaurantsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantsControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private UserService userService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private AddressMapperDTO addressMapper;

    @MockBean
    private RestaurantMapperDTO restaurantMapper;

    @MockBean
    private UserMapperDTO userMapper;

    @Test
    @WithUserDetails()
    void testRestaurantsPage() throws Exception {
        //given
        User user = DomainFixtures.someUser2();
        UserDetails userDetails = new CustomUserDetails(user, DomainFixtures.userAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);

        Address address = DomainFixtures.someAddress1();

        //when
        Mockito.when(addressService.findAddressById(any())).thenReturn(address);
        Mockito.when(addressMapper.map(address)).thenReturn(DTOFixtures.someAddress1());
        Mockito.when(userService.findUserById(user.getUserId())).thenReturn(user);
        Mockito.when(userMapper.map(any(User.class))).thenReturn(DTOFixtures.someUserDTO1());
        Mockito.when(restaurantService.findRestaurantsByDeliveryAddress(any(), any(), any())).thenReturn(
                Page.empty()
        );

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/restaurants"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user_panel/restaurants"))
                .andExpect(model().attributeExists("addressDTO"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attributeExists("restaurantDTOs"))
                .andExpect(model().attributeExists("totalPagesNumber"))
                .andExpect(model().attributeExists("currentPage"));


    }


}