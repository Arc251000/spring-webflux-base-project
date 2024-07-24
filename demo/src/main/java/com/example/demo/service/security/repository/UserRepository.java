package com.example.demo.service.security.repository;

import com.example.demo.service.base.BaseRepository;
import com.example.demo.service.security.mappers.UserMapper;
import com.example.demo.service.security.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends BaseRepository<Users> {


     @Override
      default Mono<Users> saveRelations(Users user) {


          return deleteUserRoles(user.getId()).then(Mono.defer(() -> Flux.fromIterable(Stream.of(user.getRoles().split(","))
                  .map(id -> Long.parseLong(id))
                  .collect(Collectors.toList())
                )
                  .flatMap(role -> saveUserRoles(user.getId(),role))
                  .collectList()
                  .map(roles -> user)));

     }
     Mono<Users> findByUsernameOrEmail(String username,String email);

     @Query("INSERT INTO user_role (role_id, users_id ) " +
             "VALUES (:role_id, :user_id  ) "
              )
     Mono<Users> saveUserRoles(@Param("user_id") Long userId, @Param("role_id") Long roleId );

    @Query("DELETE FROM user_role WHERE  users_id = :user_id  " )
    Mono<Boolean> deleteUserRoles( @Param("user_id") Long userId );

}
