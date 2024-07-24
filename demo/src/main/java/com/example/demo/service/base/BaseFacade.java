package com.example.demo.service.base;

import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
public class BaseFacade <Model extends BaseModel,DTO extends BaseDTO<Model>,Repository extends BaseRepository<Model>, Service extends BaseService<Model,Repository,DTO>>{
    private Service service;

    public BaseFacade(Service service){
        this.service=service;
    }

    public Mono<DTO> create( DTO dto){
        return service.create(dto);
    }

    public Mono<DTO> update(DTO dto, long id){
        dto.setId(id);
        return service.update(dto);
    }

    public Mono<DTO> get(Long id){
        return service.get(id);
    }

    public Mono<BasePage> find(BaseCriteria criteria){
        return service.find((BaseCriteria)criteria);
    }

    public Mono<Void> delete(Long id){
        return service.softDelete(id);
    }

}
