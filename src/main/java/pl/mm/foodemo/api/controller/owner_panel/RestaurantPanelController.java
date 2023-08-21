package pl.mm.foodemo.api.controller.owner_panel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.mm.foodemo.api.dto.OrderWithDetailsDTO;
import pl.mm.foodemo.api.dto.RestaurantDTO;
import pl.mm.foodemo.api.dto.mapper.OrderWithDetailsMapperDTO;
import pl.mm.foodemo.api.dto.mapper.RestaurantMapperDTO;
import pl.mm.foodemo.business.service.OrderService;
import pl.mm.foodemo.business.service.RestaurantService;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.domain.OrderStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static pl.mm.foodemo.api.controller.owner_panel.RestaurantPanelController.PANEL;

@Controller
@AllArgsConstructor
@RequestMapping(PANEL)
public class RestaurantPanelController {

    public static final String PANEL = "/panel";
    private static final String RESTAURANT_ID = "/restaurant/{id}";

    private final RestaurantService restaurantService;
    private final OrderService orderService;

    private final RestaurantMapperDTO restaurantMapper;
    private final OrderWithDetailsMapperDTO orderWithDetailsMapper;

    @GetMapping(RESTAURANT_ID)
    public ModelAndView restaurantPage(
            @PathVariable("id") final Long id
    ) {

        RestaurantDTO restaurantDTO = restaurantMapper.map(restaurantService.findById(id));

        List<Order> orders = orderService.getAllOrdersByRestaurantIdAndStatus(id, OrderStatus.received);

        List<OrderWithDetailsDTO> orderWithDetailsDTOs = orders.stream()
                .map(orderWithDetailsMapper::map)
                .sorted(Comparator.comparing(
                        (OrderWithDetailsDTO orderWithDetailsDTO) -> orderWithDetailsDTO.getOrder().getReceivedDateTime()
                ).reversed())
                .toList();

        Map<String, Object> modelData = Map.of(
                "restaurantDTO", restaurantDTO,
                "orderWithDetailsDTOs", orderWithDetailsDTOs
        );

        return new ModelAndView("owner_panel/restaurant", modelData);
    }


}
