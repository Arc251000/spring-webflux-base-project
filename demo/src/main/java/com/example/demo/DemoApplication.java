package com.example.demo;

import com.example.demo.service.security.RoleService;
import com.example.demo.service.security.UserService;
import com.example.demo.service.security.dto.RoleDto;
import com.example.demo.service.security.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Arrays;

@SpringBootApplication
@EntityScan
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {

        RoleDto adminRole = new RoleDto();
        adminRole.setName("admin");

        RoleDto clientRole = new RoleDto();
        clientRole.setName("client");

        roleService.create(adminRole)
                .flatMap(role -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername("admin");
                    userDto.setEmail("admin@gmail.com");
                    userDto.setPassword("test");
                    userDto.setRoles(Arrays.asList(role));

                    return userService.create(userDto);
                })
                .subscribe();
        roleService.create(clientRole).subscribe();

    }
}
