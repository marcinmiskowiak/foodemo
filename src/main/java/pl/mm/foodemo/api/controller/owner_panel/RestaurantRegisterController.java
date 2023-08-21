package pl.mm.foodemo.api.controller.owner_panel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.RestaurantRegisterDTO;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.time.OffsetDateTime;
import java.util.Map;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;

@Controller
@AllArgsConstructor
@Validated
@RequestMapping(PANEL)
public class RestaurantRegisterController {

    private static final String REGISTER_RESTAURANT = "/register_restaurant";

    private final RestaurantService restaurantService;

    @GetMapping(value = REGISTER_RESTAURANT)
    public ModelAndView registerRestaurantPage(Model model) {
        return new ModelAndView("owner_panel/register_restaurant", Map.of("restaurantRegisterDTO", RestaurantRegisterDTO.builder().build()));
    }


    @PostMapping(value = REGISTER_RESTAURANT)
    public String register(
            @AuthenticationPrincipal final CustomUserDetails userDetails,
            @Valid @ModelAttribute("restaurantRegisterDTO") final RestaurantRegisterDTO restaurantRegisterDTO,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "error";
        }

        Address address = buildAddress(restaurantRegisterDTO);
        Restaurant restaurant = buildRestaurant(userDetails, restaurantRegisterDTO, address);

        Restaurant restaurantSaved = restaurantService.save(restaurant);

        return "redirect:/panel/restaurant/%s".formatted(restaurantSaved.getRestaurantId());
    }

    private Restaurant buildRestaurant(CustomUserDetails userDetails, RestaurantRegisterDTO restaurantRegisterDTO, Address address) {
        return Restaurant.builder()
                .name(restaurantRegisterDTO.getName())
                .phone(restaurantRegisterDTO.getPhone())
                .registrationDateTime(OffsetDateTime.now())
                .address(address)
                .owner(userDetails.getUser())
                .build();
    }

    private Address buildAddress(RestaurantRegisterDTO restaurantRegisterDTO) {
        return Address.builder()
                .country(restaurantRegisterDTO.getCountry())
                .apartmentNumber(restaurantRegisterDTO.getApartmentNumber())
                .buildingNumber(restaurantRegisterDTO.getBuildingNumber())
                .city(restaurantRegisterDTO.getCity())
                .street(restaurantRegisterDTO.getStreet())
                .postalCode(restaurantRegisterDTO.getPostalCode())
                .build();
    }
}
