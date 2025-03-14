package com.example.holyclick.user.model;

import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private User user;
    private ActivePersonaRepository activePersonaRepository;

    public CustomUserDetails(User user, ActivePersonaRepository activePersonaRepository) {
        this.user = user;
        this.activePersonaRepository = activePersonaRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_USER";
        ActivePersona activePersona = activePersonaRepository.findByUser(user).orElse(null);
        if (activePersona != null) {
            role = "ROLE_" + activePersona.getType();
        }
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
