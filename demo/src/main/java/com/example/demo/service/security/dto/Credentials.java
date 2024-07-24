package com.example.demo.service.security.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {

    @NotNull
    @NotEmpty
    private String usernameOrEmail;
    @NotNull
    @NotEmpty
    private String password;

}
