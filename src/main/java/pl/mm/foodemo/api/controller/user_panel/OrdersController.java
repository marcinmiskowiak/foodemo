package pl.mm.foodemo.api.controller.user_panel;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.OrderWithDetailsDTO;
import pl.mm.foodemo.api.dto.mapper.OrderWithDetailsMapperDTO;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.infrastructure.security.CustomUserDetails;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static pl.mm.foodemo.api.controller.user_panel.RestaurantController.USER;

@Controller
@AllArgsConstructor
@RequestMapping(USER)
public class OrdersController {

    public static final String ORDERS = "/orders";

    private final OrderService orderService;

    private final OrderWithDetailsMapperDTO orderWithDetailsMapper;

    @GetMapping(value = ORDERS)
    public ModelAndView ordersPage(
            @AuthenticationPrincipal final CustomUserDetails userDetails
    ) {

        List<Order> orders = orderService.getAllOrdersByUserId(userDetails.getUser().getUserId());

        List<OrderWithDetailsDTO> orderWithDetailsDTOs = orders.stream().map(orderWithDetailsMapper::map
        ).sorted(Comparator.comparing(
                        (OrderWithDetailsDTO orderWithDetailsDTO) -> orderWithDetailsDTO.getOrder().getReceivedDateTime()
                ).reversed())
                .toList();

        Map<String, List<OrderWithDetailsDTO>> modelData = Map.of(
                "orderWithDetailsDTOs", orderWithDetailsDTOs
        );

        return new ModelAndView("user_panel/orders", modelData);
    }
}
