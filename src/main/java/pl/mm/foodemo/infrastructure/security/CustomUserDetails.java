package pl.mm.foodemo.infrastructure.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private pl.mm.foodemo.domain.User user;


    public CustomUserDetails(pl.mm.foodemo.domain.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), user.getActive(), true, true, true, authorities);
        this.user = user;
    }
}