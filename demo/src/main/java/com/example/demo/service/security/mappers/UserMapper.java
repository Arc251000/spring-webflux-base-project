package com.example.demo.service.security.mappers;

import com.example.demo.service.base.BaseCriteria;
import com.example.demo.service.base.BaseMapper;
import com.example.demo.service.security.RoleService;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.dto.UserDto;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserMapper implements BaseMapper<Users, UserDto> {

    @Autowired
    RoleService roleService;

    @Override
    public Mono<UserDto> mapToDTO(Users source){

        BaseCriteria criteria = new BaseCriteria();
        criteria.setFilters(Map.of(
                        "user_role", Map.of("users_id", source.getId())
                )
        );
        return roleService.findWithoutPagination(criteria)
                .map(roles -> {

                    UserDto dto = new UserDto();
                    dto.setId(source.getId());
                    dto.setUsername(source.getUsername());
                    dto.setEmail(source.getEmail());
                    dto.setRoles(roles);

                    return dto;
                });
    }
    @Override
    public Mono<Users> mapToModel(UserDto source){
        return Mono.just(new Users())
                .map(user -> {
                    user.setId(source.getId());
                    user.setUsername(source.getUsername());
                    user.setEmail(source.getEmail());
                    user.setRoles(mapRoles(source.getRoles()));
                    return user;
                });
    }

    public String mapRoles(List<RoleDto> source){

        if(source == null)
            return "";

        String roles = "";
        for(RoleDto role: source){
            roles+= role.getId()+",";
        }

        return roles.substring(0,roles.length()-1);
    }


    public List<Long> mapRoles(String source){
        return Stream.of(source.split(","))
                .map(id -> Long.parseLong(id))
                .collect(Collectors.toList());
    }


}
