package com.example.demo.service.security.dto;

import com.example.demo.service.base.BaseDTO;
import com.example.demo.service.security.model.Role;
import lombok.Data;

@Data
public class RoleDto extends BaseDTO<Role> {
    private String name;
}
