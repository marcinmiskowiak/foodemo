package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.MealOrderDTO;
import pl.mm.foodemo.api.dto.OrderDTO;
import pl.mm.foodemo.api.dto.OrderWithDetailsDTO;
import pl.mm.foodemo.domain.MealOrder;
import pl.mm.foodemo.domain.Order;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderWithDetailsMapperDTO {

    private final OrderMapperDTO orderMapper;
    private final MealOrderMapperDTO mealOrderMapper;

    public OrderWithDetailsDTO map(Order order) {
        BigDecimal totalPrice = order.getMealOrders().stream()
                .map(mealOrder -> mealOrder.getPrice().multiply(BigDecimal.valueOf(mealOrder.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderWithDetailsDTO.builder()
                .order(orderMapper.map(order))
                .canBeCancelled(
                        Duration.between(order.getReceivedDateTime(), OffsetDateTime.now()).toMinutes() <= 20
                )
                .totalPrice(totalPrice)
                .build();
    }

    public OrderWithDetailsDTO map(Map.Entry<Order, List<MealOrder>> entry) {
        OrderDTO order = orderMapper.map(entry.getKey());
        List<MealOrderDTO> mealOrders = entry.getValue().stream().map(mealOrderMapper::map).collect(Collectors.toList());

        BigDecimal totalPrice = mealOrders.stream()
                .map(mealOrder -> mealOrder.getPrice().multiply(BigDecimal.valueOf(mealOrder.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderWithDetailsDTO.builder()
                .mealOrders(mealOrders)
                .order(order)
                .totalPrice(totalPrice)
                .canBeCancelled(
                        Duration.between(order.getReceivedDateTime(), OffsetDateTime.now()).toMinutes() <= 20
                )
                .build();

    }
}
