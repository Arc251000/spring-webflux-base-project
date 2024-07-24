package com.example.demo.dist.rest;

import com.example.demo.facade.RoleFacade;
import com.example.demo.service.security.RoleService;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.repository.RoleRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/roles")
public class RoleController extends BaseController<Role, RoleDto, RoleRepository, RoleService, RoleFacade>{
    public RoleController(RoleFacade facade) {
        super(facade);
    }
}
