package com.example.demo.service.security;

import com.example.demo.service.base.BaseMapper;
import com.example.demo.service.base.BaseService;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.mappers.RoleMapper;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.repository.RoleRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, RoleRepository, RoleDto> {
    public RoleService(RoleRepository repository, R2dbcEntityTemplate entityTemplate, RoleMapper mapper ) {
        super(repository, entityTemplate, Role.class, mapper);
    }
}
