package com.example.holyclick.user.service;

import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.user.model.CustomUserDetails;
import com.example.holyclick.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private com.example.holyclick.user.repository.UserRepository userRepository;

    @Autowired
    private ActivePersonaRepository activePersonaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user, activePersonaRepository);
    }
}