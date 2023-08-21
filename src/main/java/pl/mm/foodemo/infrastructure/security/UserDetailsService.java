package pl.mm.foodemo.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.database.repository.mapper.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserJpaRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(userName).orElseThrow(() -> new NotFoundException("User not found"));

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return new CustomUserDetails(userMapper.map(user), authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .distinct()
                .collect(Collectors.toList());
    }


}

