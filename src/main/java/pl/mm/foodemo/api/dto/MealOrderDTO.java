package pl.mm.foodemo.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class MealOrderDTO {

    Long mealOrderId;
    Integer quantity;
    BigDecimal price;
    MealDTO meal;
    OrderDTO order;

}
