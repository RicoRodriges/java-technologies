package config.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    TEACHER("ROLE_TEACHER"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String roleName;

    @Override
    public String toString() {
        return roleName;
    }
}
