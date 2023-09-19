package com.nelumbo.zoo.security;

import com.nelumbo.zoo.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Aquí, asumimos que el objeto UserEntity tiene un método getRole() que devuelve el rol del usuario.
        // Debes ajustarlo según la estructura de tu objeto UserEntity.
        String userRole = user.getRole();

        // Añadir el rol del usuario como GrantedAuthority.
        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(userRole)));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public String getName() {
        return user.getName();
    }

    public String getRole() {
        return user.getRole();
    }
}