package com.example.demo.facade;

import com.example.demo.service.base.BaseCriteria;
import com.example.demo.service.base.BaseFacade;
import com.example.demo.service.base.BasePage;
import com.example.demo.service.base.PageRequestDTO;
import com.example.demo.service.security.RoleService;
import com.example.demo.service.security.UserService;
import com.example.demo.service.security.dto.Credentials;
import com.example.demo.service.security.dto.UserDto;
import com.example.demo.service.security.model.Users;
import com.example.demo.service.security.repository.RoleRepository;
import com.example.demo.service.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class UserFacade extends BaseFacade<Users,UserDto, UserRepository, UserService> {
    public UserFacade(UserService service) {
        super(service);
    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;
    /*@Override
    public Mono<UserDto> create(UserDto dto){

        return getService().create(dto)
                .flatMap(newUser -> {

                    BaseCriteria criteria = new BaseCriteria();
                    criteria.setFilters(Map.of(
                                    "user_role", Map.of("users_id", newUser.getId())
                            )
                    );
                    return roleService.findWithoutPagination(criteria)
                            .map(roles -> {
                                newUser.setRoles(roles);
                                return newUser;
                            });
                });
    }

    @Override
    public Mono<UserDto> update(UserDto dto, long id) {
        return super.update(dto, id)
                .flatMap(newUser -> {

                    BaseCriteria criteria = new BaseCriteria();
                    criteria.setFilters(Map.of(
                                    "user_role", Map.of("users_id", newUser.getId())
                            )
                    );
                    return roleService.findWithoutPagination(criteria)
                            .map(roles -> {
                                newUser.setRoles(roles);
                                return newUser;
                            });
                });

    }

    @Override
    public Mono<UserDto> get(Long id) {
        return super.get(id).flatMap(newUser -> {

            BaseCriteria criteria = new BaseCriteria();
            criteria.setFilters(Map.of(
                            "user_role", Map.of("users_id", newUser.getId())
                    )
            );
            return roleService.findWithoutPagination(criteria)
                    .map(roles -> {
                        newUser.setRoles(roles);
                        return newUser;
                    });
        });
    }

    @Override
    public Mono<BasePage> find(BaseCriteria userCriteria) {
        return super.find(userCriteria).flatMap(page -> Flux.fromIterable((List<UserDto>)page.getData())
                .flatMap(newUser->{

            BaseCriteria criteria = new BaseCriteria();
            criteria.setFilters(Map.of(
                            "user_role", Map.of("users_id", newUser.getId())
                    )
            );
            return roleService.findWithoutPagination(criteria)
                    .map(roles -> {
                        newUser.setRoles(roles);
                        return newUser;
                    });
        })
                        .collectList().map(users->{
                            page.setData(users);
                            return page;
                })
        );
    }*/

    public Mono<UserDto> login(Credentials credentials){
        return getService().login(credentials);
    }

    public Mono<Boolean> logout(){
        return getService().logout();
    }

}
