package com.example.demo.service.security;

import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.service.base.BaseService;
import com.example.demo.service.security.dto.Credentials;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.dto.UserDto;
import com.example.demo.service.security.mappers.UserMapper;
import com.example.demo.service.security.model.Role;
import com.example.demo.service.security.model.Roles;
import com.example.demo.service.security.model.Users;
import com.example.demo.service.security.repository.RoleRepository;
import com.example.demo.service.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService extends BaseService<Users, UserRepository, UserDto> {
    public UserService(UserRepository repository, R2dbcEntityTemplate entityTemplate, UserMapper mapper) {
        super(repository, entityTemplate, Users.class, mapper);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Mono<UserDto> create(UserDto dto){

        return getMapper().mapToModel(dto)
                        .map(newUser -> {
                            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
                            return newUser;
                        })
                        .flatMap( newUser -> getRepository().saveWithRelations(newUser)
                                                .flatMap(user-> getMapper().mapToDTO(user).map(mappedUser -> {
                                                    mappedUser.setToken(jwtProvider.generateToken(user));
                                                    return mappedUser;
                                                })
        ));
    }

    @Override
    public Mono<UserDto> update(UserDto dto){
        return getRepository().findById(dto.getId())
                        .filter(oldUser -> !oldUser.getDeleted())
                        .map(oldUser -> {
                            oldUser.setUsername(dto.getUsername());
                            oldUser.setRoles(((UserMapper)getMapper()).mapRoles(dto.getRoles()));
                            return getRepository().saveWithRelations(oldUser);
                        })
                .flatMap(model -> model)
                .flatMap(newUser -> getMapper().mapToDTO(newUser));
    }

    public Mono<UserDto> login(Credentials credentials){
            return getRepository()
                    .findByUsernameOrEmail(credentials.getUsernameOrEmail(),credentials.getUsernameOrEmail())
                    .filter(user -> !user.getDeleted())
                    .filter(user -> passwordEncoder.matches(credentials.getPassword(),user.getPassword()))
                    .flatMap(user -> roleRepository.findRoleByUser(user.getId()).collectList().map(roles -> {

                        String rolesAux = "";
                        for(Role role: roles){
                            rolesAux+= role.getId()+",";
                        }

                        user.setRoles(rolesAux);
                        return user;

                    }))
                    .flatMap(user -> {
                        String token = jwtProvider.generateToken(user);

                        return getMapper().mapToDTO(user)
                                .map(dto -> {
                                    dto.setToken(token);
                                    return dto;
                                });
                    })
                    .switchIfEmpty(Mono.error(new Exception("Bad credentials")));
    }

    public Mono<Boolean> logout(){
        return Mono.just(true);
    }



}
