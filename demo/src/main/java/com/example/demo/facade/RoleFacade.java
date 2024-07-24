package com.example.demo.facade;

import com.example.demo.service.base.BaseFacade;
import com.example.demo.service.security.RoleService;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleFacade extends BaseFacade<Role, RoleDto, RoleRepository, RoleService> {
    public RoleFacade(RoleService service) {
        super(service);
    }
}
