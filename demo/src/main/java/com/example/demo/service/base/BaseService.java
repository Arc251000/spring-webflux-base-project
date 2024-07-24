package com.example.demo.service.base;

import lombok.Getter;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;
 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public abstract class BaseService<Model extends BaseModel,Repository extends BaseRepository<Model>,DTO extends BaseDTO<Model>> {

    R2dbcEntityTemplate entityTemplate;
    Repository repository;
    Class modelClass;
    BaseMapper<Model,DTO> mapper;

    public BaseService(Repository repository,R2dbcEntityTemplate entityTemplate, Class modelClass,BaseMapper<Model,DTO> mapper){
        this.repository = repository;
        this.entityTemplate = entityTemplate;
        this.modelClass = modelClass;
        this.mapper = mapper;
    }

    public Mono<DTO> create(DTO dto){
        return mapper.mapToModel(dto).flatMap( model -> repository.saveWithRelations(model)
                .flatMap(element -> mapper.mapToDTO(element)));
    }

    public Mono<DTO> update(DTO dto){
        return repository.findById(dto.getId())
                .filter(model -> !model.getDeleted())
                .map(element -> {
                            Field[] fields = dto.getClass().getDeclaredFields();

                            for (Field field : fields) {
                                if (dto.getOnlyRead().contains(field.getName())) {continue;}
                                field.setAccessible(true);
                                try {
                                    Field oldField = element.getClass().getDeclaredField(field.getName());
                                    oldField.setAccessible(true);
                                    oldField.set(element, field.get(dto));
                                }
                                catch(Exception e){
                                    System.out.println(e);
                                }

                            }
                            return element;
                        })
                .flatMap(element -> repository.saveWithRelations(element))
                .flatMap(element -> mapper.mapToDTO(element));

    }


    public Mono<DTO> get(Long id){
        return repository.findById(id)
                .filter(model -> !model.getDeleted())
                .flatMap(element -> mapper.mapToDTO(element));
    }

    public Mono<BasePage> find(BaseCriteria criteria){

        return entityTemplate.getDatabaseClient().sql(criteria.getCountPredicate(modelClass))
                .map((totalElements, meta) ->{
                    return entityTemplate.getDatabaseClient().sql(criteria.getPredicate(modelClass))
                            .map((row, metadata) -> {
                                List<Field> fields = new ArrayList();
                                Class modelClassAux = modelClass;

                                while (modelClassAux != null) {
                                    fields.addAll(Arrays.asList(modelClassAux.getDeclaredFields()));
                                    modelClassAux = modelClassAux.getSuperclass();
                                }
                                Model object = null;

                                try {
                                    object = (Model) modelClass.getConstructor().newInstance();
                                }
                                catch(Exception e){}

                                for (Field field : fields) {

                                    try {
                                        field.setAccessible(true);
                                        field.set(object, row.get(field.getName(),field.getType()));
                                    }
                                    catch(Exception e){
                                        System.out.println(e);
                                    }

                                }
                                return mapper.mapToDTO(object);
                            })
                            .all()
                            .flatMap(model -> model)
                            .collectList()
                            .map(data -> mapper.mapToPage(data,totalElements.get("totalelements",Long.class),criteria));
                }).one().flatMap(mono -> mono);

    }

    public Mono<List<DTO>> findWithoutPagination(BaseCriteria criteria){
        return entityTemplate.getDatabaseClient().sql(criteria.getPredicate(modelClass))
                .map((row, metadata) -> {
                    List<Field> fields = new ArrayList();
                    Class modelClassAux = modelClass;

                    while (modelClassAux != null) {
                        fields.addAll(Arrays.asList(modelClassAux.getDeclaredFields()));
                        modelClassAux = modelClassAux.getSuperclass();
                    }
                    Model object = null;

                    try {
                        object = (Model) modelClass.getConstructor().newInstance();
                    }
                    catch(Exception e){}

                    for (Field field : fields) {

                        try {
                            field.setAccessible(true);
                            field.set(object, row.get(field.getName(),field.getType()));
                        }
                        catch(Exception e){
                            System.out.println(e);
                        }

                }
                    return mapper.mapToDTO(object);
                })
                .all()
                .flatMap(model -> model)
                .collectList();
    }

    public Mono<Void> softDelete(Long id){
        return repository.findById(id).flatMap(model -> {
            model.setDeleted(true);
            return repository.save(model);
        }).flatMap(model -> Mono.empty());
    }

    public Mono<Void> hardDelete(Long id){
        return repository.deleteById(id);
    }


}
