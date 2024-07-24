package com.example.demo.service.base;

import reactor.core.publisher.Mono;

import java.util.List;

public interface BaseMapper <Model extends BaseModel,DTO extends BaseDTO>{

    Mono<DTO> mapToDTO(Model source);

    Mono<Model> mapToModel(DTO source);

    default BasePage mapToPage(List source,long totalElements,BaseCriteria criteria){
        BasePage page = new BasePage();
        page.setData((List<Object>) source);
        page.setTotalElements(totalElements);
        page.setPage(criteria.getPagination().getPage());
        page.setSize(criteria.getPagination().getSize()==source.size()?criteria.getPagination().getSize():source.size());

        return page;
    }

}
