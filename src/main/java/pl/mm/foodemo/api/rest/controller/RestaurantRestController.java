package pl.mm.foodemo.api.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mm.foodemo.api.dto.RestaurantsDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.RestaurantService;

import java.util.stream.Collectors;

import static pl.mm.foodemo.api.rest.controller.RestaurantRestController.API_RESTAURANTS;

@RestController
@AllArgsConstructor
@RequestMapping(API_RESTAURANTS)
public class RestaurantRestController {

    public static final String API_RESTAURANTS = "/api/restaurants";

    private final RestaurantService restaurantService;
    private final RestaurantMapperDTO restaurantMapper;

    @GetMapping
    public RestaurantsDTO restaurantsData() {
        return RestaurantsDTO.builder()
                .restaurants(
                        restaurantService.findAll().stream().map(restaurantMapper::map).collect(Collectors.toList())
                )
                .build();
    }
}
