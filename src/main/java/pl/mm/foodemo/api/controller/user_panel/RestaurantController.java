package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.MealDTO;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.mapper.MealMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.MealCategory;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.mm.foodemo.api.controller.user_panel.RestaurantController.USER;

@Controller
@AllArgsConstructor
@RequestMapping(USER)
public class RestaurantController {

    public static final String USER = "/user";
    public static final String RESTAURANT_ID = "/restaurant/{id}";

    private final RestaurantService restaurantService;
    private final MealService mealService;

    private final RestaurantMapperDTO restaurantMapper;
    private final MealMapperDTO mealMapper;

    @GetMapping(RESTAURANT_ID)
    public ModelAndView userRestaurantPage(
            @PathVariable("id") final Long id
    ) {

        Restaurant restaurant = restaurantService.findById(id);
        RestaurantDTO restaurantDTO = restaurantMapper.map(restaurant);

        List<MealDTO> restaurantAllMeals = mealService.findAllByRestaurantId(restaurant).stream().map(mealMapper::map).toList();

        Map<MealCategory, List<MealDTO>> restaurantMealsByCategories = restaurantAllMeals.stream().collect(Collectors.groupingBy(MealDTO::getCategory));

        Map<String, Object> modelData = Map.of(
                "restaurantDTO", restaurantDTO,
                "restaurantMealsByCategories", restaurantMealsByCategories
        );

        return new ModelAndView("user_panel/restaurant", modelData);
    }


}
