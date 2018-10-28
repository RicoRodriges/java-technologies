package config.security;

import dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

@Getter
public class SpringUser implements UserDetails {

    private final UserDto user;
    private final Set<? extends GrantedAuthority> authorities;

    public SpringUser(UserDto user) {
        this.user = user;
        UserRole role = user.getIsTutor() ? UserRole.ADMIN : UserRole.USER;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
