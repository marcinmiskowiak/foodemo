package pl.mm.foodemo.api.controller.owner_panel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Restaurant;

import java.util.Map;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;

@Controller
@AllArgsConstructor
@Validated
@RequestMapping(PANEL)
public class EditRestaurantProfileController {

    private static final String RESTAURANT_ID_EDIT_PROFILE = "/restaurant/{id}/edit/profile";
    private static final String ADDRESS = "/address";


    private final AddressService addressService;
    private final RestaurantService restaurantService;

    private final AddressMapperDTO addressMapper;

    @GetMapping(RESTAURANT_ID_EDIT_PROFILE)
    public ModelAndView editProfilePage(
            @PathVariable("id") final Long id
    ) {

        Restaurant restaurant = restaurantService.findById(id);

        AddressDTO restaurantAddress = addressMapper.map(
                addressService.findAddressById(restaurant.getAddress().getAddressId())
        );

        Map<String, Object> modelData = Map.of(
                "addressDTO", restaurantAddress,
                "restaurantId", restaurant.getRestaurantId(),
                "restaurantName", restaurant.getName()
        );

        return new ModelAndView("owner_panel/restaurant_edit_profile", modelData);
    }


    @PutMapping(RESTAURANT_ID_EDIT_PROFILE + ADDRESS)
    public String editAddress(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "error";
        }

        restaurantService.updateAddress(id, addressMapper.map(addressDTO));

        return "redirect:/panel/restaurant/" + id;
    }
}
