package pl.mm.foodemo.api.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Role;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class UserDTO {
    Long userId;
    String name;
    String surname;
    String email;
    String password;
    String phone;
    OffsetDateTime registrationDateTime;
    Address address;
    Boolean active;
    Set<OrderDTO> orders;
    Set<Role> roles;

}
