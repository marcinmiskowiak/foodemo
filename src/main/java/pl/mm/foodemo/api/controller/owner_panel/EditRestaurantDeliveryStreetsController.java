package pl.mm.foodemo.api.controller.owner_panel;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddingDeliveryStreetsDTO;
import pl.mm.foodemo.api.dto.PairDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.DeliveryAddressService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;


@Controller
@AllArgsConstructor
@Validated
@RequestMapping(PANEL)
public class EditRestaurantDeliveryStreetsController {

    private static final String RESTAURANT_ID_EDIT_STREETS = "/restaurant/{id}/edit/streets";
    private static final String ADD = "/add";

    private final RestaurantService restaurantService;
    private final DeliveryAddressService deliveryAddressService;
    private final AddressService addressService;

    @GetMapping(value = RESTAURANT_ID_EDIT_STREETS)
    public ModelAndView editDeliveryStreetsPage(
            @PathVariable("id") final Long id
    ) {

        Restaurant restaurant = restaurantService.findById(id);
        AddingDeliveryStreetsDTO addingDeliveryStreetsDTO = AddingDeliveryStreetsDTO.builder().build();

        Map<String, List<String>> deliveryStreets = deliveryAddressService.getAllByRestaurant(restaurant);

        Map<PairDTO<String>, List<String>> deliveryStreets2 = deliveryStreets.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> new PairDTO<String>(entry.getKey(), addressService.getCityByPostalCode(entry.getKey())),
                        Map.Entry::getValue
                ));

        Map<String, Object> modelData = Map.of(
                "restaurantId", restaurant.getRestaurantId(),
                "restaurantName", restaurant.getName(),
                "addingDeliveryStreetsDTO", addingDeliveryStreetsDTO,
                "deliveryStreets", deliveryStreets2
        );

        return new ModelAndView("owner_panel/restaurant_edit_streets", modelData);
    }

    @PostMapping(RESTAURANT_ID_EDIT_STREETS + ADD)
    public String addDeliveryStreets(
            @PathVariable("id") final Long id,
            @Valid @ModelAttribute("addingDeliveryStreetsDTO") final AddingDeliveryStreetsDTO addingDeliveryStreetsDTO,
            BindingResult result,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer
    ) {
        if (result.hasErrors()) {
            return "error";
        }

        deliveryAddressService.addDeliveryStreets(id, addingDeliveryStreetsDTO.getPostalCode(), addingDeliveryStreetsDTO.getStreets());

        return "redirect:" + referrer;
    }
}
