package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.AddressDTO;
import pl.mm.foodemo.api.dto.mapper.AddressMapperDTO;
import pl.mm.foodemo.business.service.AddressService;
import pl.mm.foodemo.business.service.MealService;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.domain.Meal;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.domain.OrderStatus;
import pl.mm.foodemo.domain.exception.EmptyOrderException;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static pl.mm.foodemo.api.controller.user_panel.RestaurantController.USER;


@Controller
@AllArgsConstructor
@RequestMapping(USER)
public class OrderController {

    private static final String RESTAURANT_ID= "/restaurant/{id}";
    private static final String ORDER = "/order";
    private static final String CONFIRM_ID= "/confirm/{id}";
    private static final String DELETE_ID= "/delete/{id}";


    private final MealService mealService;
    private final AddressService addressService;
    private final OrderService orderService;

    private final AddressMapperDTO addressMapper;


    @PostMapping(RESTAURANT_ID + ORDER)
    public ModelAndView processOrder(
            @PathVariable("id") final Long id,
            @RequestParam final Map<String, String> selectedMeals,
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {
        Map<Meal, String> filteredSelectedMeals = getFilteredSelectedMeals(selectedMeals);

        if(filteredSelectedMeals.size() < 1) {
            throw new EmptyOrderException("You must order something");
        }

        BigDecimal totalAmount = orderService.calculateTotalAmount(filteredSelectedMeals);

        AddressDTO userAddress = addressMapper.map(addressService.findAddressById(userDetails.getUser().getAddress().getAddressId()));

        Order order = orderService.makeOrder(filteredSelectedMeals, id, userDetails.getUser().getUserId());

        Map<String, Object> modelData = Map.of(
                "selectedMeals", filteredSelectedMeals,
                "totalAmount", totalAmount,
                "userAddressDTO", userAddress,
                "userDetails", userDetails.getUser(),
                "restaurantId", id,
                "orderId", order.getOrderId()
        );
        return new ModelAndView("user_panel/order_details",
                modelData
        );
    }


    @PatchMapping(ORDER + CONFIRM_ID)
    public String confirmOrder(@PathVariable("id") Long id) {

        orderService.setOrderStatusById(OrderStatus.received, id);

        return "redirect:/user/orders";
    }

    @DeleteMapping(ORDER + DELETE_ID)
    public String deleteOrder(@PathVariable("id") Long id) {

        orderService.deleteOrderById(id);

        return "redirect:/user/orders";
    }

    private Map<Meal, String> getFilteredSelectedMeals(Map<String, String> selectedMealsMap) {
        return selectedMealsMap.entrySet()
                .stream()
                .filter(entry -> !Objects.equals(entry.getKey(), "_csrf"))
                .filter(entry -> !Objects.equals(entry.getValue(), "0"))
                .collect(Collectors.toMap(
                        entry -> mealService.findById(Long.valueOf(entry.getKey())),
                        Map.Entry::getValue
                ));
    }



}
