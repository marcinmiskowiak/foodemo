package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;


@With
@Value
@Builder
public class MealOrder {

    Long mealOrderId;
    Integer quantity;
    BigDecimal price;
    Meal meal;
    Order order;


}