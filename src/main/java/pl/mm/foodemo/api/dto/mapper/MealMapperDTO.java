package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.MealDTO;
import pl.mm.foodemo.domain.Meal;

@Component
@AllArgsConstructor
public class MealMapperDTO {
    public MealDTO map(Meal meal) {
        return MealDTO.builder()
                .mealId(meal.getMealId())
                .name(meal.getName())
                .price(meal.getPrice())
                .category(meal.getCategory())
                .imageName(meal.getImageName())
                .description(meal.getDescription())
                .build();
    }

    public Meal map(MealDTO meal) {
        return Meal.builder()
                .mealId(meal.getMealId())
                .name(meal.getName())
                .price(meal.getPrice())
                .category(meal.getCategory())
                .imageName(meal.getImageName())
                .description(meal.getDescription())
                .build();
    }
}
