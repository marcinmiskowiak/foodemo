package pl.mm.foodemo.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class AddressDTO {

    @JsonIgnore
    Long addressId;

    @NotBlank(message = "Country is required")
    @Size(max = 32, message = "Country cannot be longer than 32 characters")
    String country;

    @NotBlank(message = "City is required")
    @Size(max = 32, message = "City cannot be longer than 32 characters")
    String city;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in format XX-XXX")
    String postalCode;

    @NotBlank(message = "Street is required")
    @Size(max = 32, message = "Street cannot be longer than 32 characters")
    String street;
    @NotBlank(message = "Building Number is required")
    @Size(max = 32, message = "Building Number cannot be longer than 32 characters")
    String buildingNumber;
    @Size(max = 32, message = "Building Number cannot be longer than 32 characters")
    String apartmentNumber;


}