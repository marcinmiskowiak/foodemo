package pl.mm.foodemo.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class UserRegisterDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 32, message = "Name cannot be longer than 32 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(max = 32, message = "Surname cannot be longer than 32 characters")
    private String surname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 64, message = "Email cannot be longer than 64 characters")
    private String email;

    @Size(min = 3, max = 128, message = "Password should be between 8 and 128 characters long")
    private String password;

    @Pattern(regexp = "\\+\\d{2} \\d{3} \\d{3} \\d{3}", message = "Phone should be in format +XX XXX XXX XXX")
    private String phone;


    @NotBlank(message = "Country is required")
    @Size(max = 32, message = "Country cannot be longer than 32 characters")
    private String country;


    @NotBlank(message = "City is required")
    @Size(max = 32, message = "City cannot be longer than 32 characters")
    private String city;


    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in format XX-XXX")
    private String postalCode;


    @NotBlank(message = "Street is required")
    @Size(max = 32, message = "Street cannot be longer than 32 characters")
    private String street;


    @NotBlank(message = "Building Number is required")
    @Size(max = 32, message = "Building Number cannot be longer than 32 characters")
    private String buildingNumber;


    @Size(max = 32, message = "Building Number cannot be longer than 32 characters")
    private String apartmentNumber;

    private Boolean isRestaurantOwner;

}





