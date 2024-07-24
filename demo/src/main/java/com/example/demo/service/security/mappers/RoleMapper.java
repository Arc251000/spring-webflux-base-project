package com.example.demo.service.security.mappers;

import com.example.demo.service.base.BaseMapper;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoleMapper implements BaseMapper<Role, RoleDto> {


    @Override
    public Mono<RoleDto> mapToDTO(Role source) {
        return Mono.just(new RoleDto())
                .map(dto -> {
                    dto.setId(source.getId());
                    dto.setName(source.getName());

                    return dto;
                });
    }

    @Override
    public Mono<Role> mapToModel(RoleDto source) {
        return Mono.just(new Role())
                .map(role -> {
                    role.setId(source.getId());
                    role.setName(source.getName());

                    return role;
                });
    }
}
