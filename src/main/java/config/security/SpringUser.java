package config.security;

import dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Getter
public class SpringUser implements UserDetails {

    private final UserDto user;
    private final Set<? extends GrantedAuthority> authorities;

    public SpringUser(UserDto user) {
        this.user = user;
        UserRole role;
        if (user.getIsTutor()) {
            if (user.getUniversity() == null) {
                role = UserRole.ADMIN;
            } else {
                role = UserRole.TEACHER;
            }
        } else {
            role = UserRole.USER;
        }
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role.toString()));
        if (role.equals(UserRole.ADMIN)) {
            roles.add(new SimpleGrantedAuthority(UserRole.TEACHER.toString()));
        }
        this.authorities = roles;
    }

    public boolean isTutor() {
        return user.getIsTutor() && user.getUniversity() == null;
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
