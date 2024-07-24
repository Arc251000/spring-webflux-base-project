package com.example.demo.service.base;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public interface BaseRepository <Model extends BaseModel> extends R2dbcRepository<Model, Serializable>{

    default Mono<Model> saveWithRelations(Model model){
        return save(model).flatMap(element-> {
            model.setId(element.getId());
            return this.saveRelations(model);
        }
        );
    }

    default Mono<Model> saveRelations(Model model){
        return Mono.just(model);
    }


}
