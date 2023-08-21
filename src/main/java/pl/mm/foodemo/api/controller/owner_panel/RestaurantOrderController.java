package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.domain.OrderStatus;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;
@Controller
@AllArgsConstructor
@RequestMapping(PANEL)
public class RestaurantOrderController {

    private static final String RESTAURANT_ID_ORDER_COMPLETE_ID = "/restaurant/{resId}/order/complete/{orderId}";

    private final OrderService orderService;

    @PatchMapping(RESTAURANT_ID_ORDER_COMPLETE_ID)
    public String completeOrder(
            @PathVariable("orderId") final Long orderId,
            @PathVariable("resId") final Long restaurantId
    ) {

        orderService.setOrderStatusById(OrderStatus.completed, orderId);

        return "redirect:/panel/restaurant/" + restaurantId;
    }

}
