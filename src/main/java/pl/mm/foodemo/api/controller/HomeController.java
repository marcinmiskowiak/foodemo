package pl.mm.foodemo.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.MealOrderDTO;
import pl.mm.foodemo.api.dto.mapper.MealOrderMapperDTO;
import pl.mm.foodemo.business.service.MealOrderService;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static pl.mm.foodemo.api.controller.HomeController.HOME;

@Controller
@AllArgsConstructor
@RequestMapping(HOME)
public class HomeController {

    static final String HOME = "/";

    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final MealService mealService;
    private final MealOrderService mealOrderService;

    private final MealOrderMapperDTO mealOrderMapperDTO;

    @GetMapping
    public ModelAndView homePage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (Objects.nonNull(userDetails)) {
            return new ModelAndView("redirect:/user/restaurants");
        }


        Map<String, ?> data = prepareData();
        return new ModelAndView("index", data);
    }

    private Map<String, ?> prepareData() {
        List<MealOrderDTO> lastThreeOrderedMeals = mealOrderService.findThreeLastOrderedMeals().stream().map(mealOrderMapperDTO::map).toList();
        List<String> newestRestaurantsNames = restaurantService.findNewestRestaurantsNames(7);

        return Map.of(
                "allRestaurantsSize", restaurantService.countAll(),
                "allOrdersSize", orderService.countAll(),
                "allMealsSize", mealService.countAll(),
                "lastThreeOrderedMealsDTOs", lastThreeOrderedMeals,
                "newestRestaurantsNames", newestRestaurantsNames
        );
    }

}
