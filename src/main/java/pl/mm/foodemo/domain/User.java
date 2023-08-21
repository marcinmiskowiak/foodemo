package pl.mm.foodemo.domain;


import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;


@With
@Value
@Builder
public class User {
    Long userId;
    String name;
    String surname;
    String email;
    String password;
    String phone;
    OffsetDateTime registrationDateTime;
    Address address;
    Boolean active;
    Set<Order> orders;
    Set<Role> roles;

}