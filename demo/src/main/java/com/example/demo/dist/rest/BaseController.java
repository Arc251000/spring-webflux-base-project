package com.example.demo.dist.rest;

import com.example.demo.service.base.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


public class BaseController <Model extends BaseModel,DTO extends BaseDTO<Model>,Repository extends BaseRepository<Model>,Service extends BaseService<Model,Repository,DTO>, Facade extends BaseFacade<Model,DTO,Repository,Service>>{

    Facade facade;

    public BaseController(Facade facade){
        this.facade =facade;
    }

    @PostMapping("/")
    public Mono<DTO> create(@RequestBody @Valid DTO dto ){  return facade.create(dto); }

    @PutMapping("/{id}")
    public Mono<DTO> update(  @RequestBody @Valid DTO dto, @PathVariable(name = "id") long id){return facade.update(dto,id);}

    @GetMapping("/{id}")
    public Mono<DTO> get(@PathVariable(name = "id") Long id){
        return facade.get(id);
    }

    @PostMapping("/find")
    public Mono<BasePage> find(@RequestBody BaseCriteria criteria){
        return facade.find((BaseCriteria)criteria);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable(name = "id") Long id){
        return facade.delete(id);
    }


}
