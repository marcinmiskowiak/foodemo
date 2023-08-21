package pl.mm.foodemo.api.dto.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mm.foodemo.api.dto.MealOrderDTO;
import pl.mm.foodemo.domain.MealOrder;

@Component
@AllArgsConstructor
public class MealOrderMapperDTO {

    private final MealMapperDTO mealMapper;

    public MealOrderDTO map(MealOrder mealOrder) {
        return MealOrderDTO.builder()
                .mealOrderId(mealOrder.getMealOrderId())
                .meal(mealMapper.map(mealOrder.getMeal()))
                .price(mealOrder.getPrice())
                .quantity(mealOrder.getQuantity())
                .build();
    }
}
