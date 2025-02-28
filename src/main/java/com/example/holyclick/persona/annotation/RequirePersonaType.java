package com.example.holyclick.persona.annotation;

import com.example.holyclick.persona.model.PersonaType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePersonaType {
    PersonaType value();
} 