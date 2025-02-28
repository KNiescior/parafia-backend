package com.example.holyclick.persona.aspect;

import com.example.holyclick.persona.annotation.RequirePersonaType;
import com.example.holyclick.persona.exception.UnauthorizedPersonaException;
import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PersonaTypeValidationAspect {

    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @Before("@annotation(requirePersonaType)")
    public void validatePersonaType(RequirePersonaType requirePersonaType) {
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new UnauthorizedPersonaException("No active persona found"));

        if (activePersona.getType() != requirePersonaType.value()) {
            throw new UnauthorizedPersonaException(
                    String.format("This operation requires %s persona type", requirePersonaType.value())
            );
        }
    }
} 