package com.example.demo.service.security.repository;

import com.example.demo.service.base.BaseRepository;
import com.example.demo.service.security.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

public interface RoleRepository extends BaseRepository<Role> {

    @Query("SELECT r.* FROM Role r INNER JOIN user_role u ON r.id = u.role_id WHERE u.users_id = :userId")
    Flux<Role> findRoleByUser(Long userId);

}
