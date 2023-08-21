package pl.mm.foodemo.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Role {

    private int roleId;
    private String role;
}
