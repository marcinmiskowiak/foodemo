package pl.mm.foodemo.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@With
public class OrderWithDetailsDTO {

    OrderDTO order;
    List<MealOrderDTO> mealOrders;
    BigDecimal totalPrice;
    Boolean canBeCancelled;
}
