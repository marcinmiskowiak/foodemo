package pl.mm.foodemo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.With;


@Value
@With
@Builder
public class UserEditProfileDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 64, message = "Email cannot be longer than 64 characters")
    String email;


    @Pattern(regexp = "\\+\\d{2} \\d{3} \\d{3} \\d{3}", message = "Phone should be in format +XX XXX XXX XXX")
    String phone;
}
