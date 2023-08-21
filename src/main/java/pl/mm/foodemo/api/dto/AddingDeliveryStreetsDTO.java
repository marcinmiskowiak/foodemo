package pl.mm.foodemo.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class AddingDeliveryStreetsDTO {

    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in format XX-XXX")
    String postalCode;
    String streets;
}
