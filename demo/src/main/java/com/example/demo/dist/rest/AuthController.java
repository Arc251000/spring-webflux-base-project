package com.example.demo.dist.rest;

import com.example.demo.facade.UserFacade;
import com.example.demo.service.security.UserService;
import com.example.demo.service.security.dto.Credentials;
import com.example.demo.service.security.dto.UserDto;
import com.example.demo.service.security.model.Users;
import com.example.demo.service.security.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController<Users, UserDto, UserRepository, UserService, UserFacade> {

    public AuthController(UserFacade facade) {
        super(facade);
    }

    @PostMapping("/login")
    public Mono<UserDto> login(@RequestBody @Valid Credentials credentials){
        return facade.login(credentials);
    }

    @PostMapping("/logout")
    public Mono<Boolean> logout(){
        return facade.logout();
    }


}
