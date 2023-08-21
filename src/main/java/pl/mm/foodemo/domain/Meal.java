package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;


@With
@Value
@Builder
public class Meal {

    Long mealId;
    String name;
    MealCategory category;
    String description;
    BigDecimal price;
    String imageName;
    Restaurant restaurant;


}