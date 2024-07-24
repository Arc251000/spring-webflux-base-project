package com.example.demo.service.security.dto;

import com.example.demo.service.base.BaseDTO;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.model.Users;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto extends BaseDTO<Users> {

    private String username;

    private String email;
    private String password;
    private List<RoleDto> roles;
    @Null
    private String token;

}
