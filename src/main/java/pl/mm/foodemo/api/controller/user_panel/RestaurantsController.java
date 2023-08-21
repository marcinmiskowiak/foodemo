package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.UserDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.api.dto.mapper.UserMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.business.service.UserService;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.util.List;
import java.util.Map;

import static pl.mm.foodemo.api.controller.user_panel.RestaurantController.USER;

@Controller
@AllArgsConstructor
@RequestMapping(USER)
public class RestaurantsController {

    private static final String RESTAURANTS = "/restaurants";
    private static final String PAGE_PAGE_NUMBER = "/page/{pageNumber}";

    private final AddressService addressService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    private final AddressMapperDTO addressMapper;
    private final RestaurantMapperDTO restaurantMapper;
    private final UserMapperDTO userMapper;

    @GetMapping(RESTAURANTS)
    public ModelAndView restaurantsPage(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        return restaurantsPagingPage(1, userDetails);
    }

    @GetMapping(RESTAURANTS + PAGE_PAGE_NUMBER)
    public ModelAndView restaurantsPagingPage(
            @PathVariable("pageNumber") final Integer pageNumber,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {

        AddressDTO userAddress = addressMapper.map(addressService.findAddressById(userDetails.getUser().getAddress().getAddressId()));

        UserDTO userDTO = userMapper.map(userService.findUserById(userDetails.getUser().getUserId()));

        Page<Restaurant> restaurantsPage = restaurantService.findRestaurantsByDeliveryAddress(
                userAddress.getPostalCode(), userAddress.getStreet(), pageNumber-1
        );


        List<RestaurantDTO> restaurantDTOs = restaurantsPage
                .stream()
                .map(restaurantMapper::map)
                .toList();

        Map<String, Object> modelData = Map.of(
                "addressDTO", userAddress,
                "userDTO", userDTO,
                "restaurantDTOs", restaurantDTOs,
                "totalPagesNumber", restaurantsPage.getTotalPages(),
                "currentPage", pageNumber
        );
        return new ModelAndView("user_panel/restaurants", modelData);
    }
}
