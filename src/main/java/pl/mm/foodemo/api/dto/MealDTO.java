package pl.mm.foodemo.api.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.mm.foodemo.domain.MealCategory;

import java.math.BigDecimal;


@With
@Value
@Builder
public class MealDTO {

    Long mealId;

    @NotBlank
    @Size(min = 3, max = 32,  message = "Name cannot be longer than 32 characters and shorter than 3 characters")
    String name;

    MealCategory category;
    String description;

    @NotNull(message = "Meal ID cannot be empty")
    @DecimalMin(value = "0.00", inclusive = true, message = "Price must be equal to or greater than 0.00")
    @DecimalMax(value = "9999.99", inclusive = true, message = "Price must be equal to or less than 9999.99")
    BigDecimal price;
    String imageName;
    RestaurantDTO restaurant;


}