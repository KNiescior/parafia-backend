package com.example.holyclick.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterResponse {

    public HttpStatus status;
    public String message;
}
