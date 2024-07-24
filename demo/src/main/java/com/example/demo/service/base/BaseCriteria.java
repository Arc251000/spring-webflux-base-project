package com.example.demo.service.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

import java.util.Map;

@Getter
@Setter
public class BaseCriteria {

    private Map<String,Object> filters = Map.of();
    private PageRequestDTO pagination = new PageRequestDTO(20,0);

    public String getPredicate(Class modelClass){

        String joins = "";

        String query = " where m.deleted = false ";

        String paginatedQuery = " LIMIT " + pagination.getSize() + " OFFSET " + (pagination.getPage() * pagination.getSize());


        for(String attribute: filters.keySet()){

            Object value = filters.get(attribute);

            if(value instanceof Map){

                joins += " inner join "+attribute+" on "+attribute+"."+modelClass.getSimpleName().toLowerCase()+"_id = m.id";

                for(String relationAttribute: ((Map<String,Object>)value).keySet()){

                    Object relationValue = ((Map<String,Object>)value).get(relationAttribute);
                    query += " and "+ attribute+"."+mapAttributeToQuery(relationAttribute, relationValue);
                }
            }
            else
                query += " and m."+mapAttributeToQuery(attribute, value);

        }

        System.out.println("SELECT m.* from "+modelClass.getSimpleName()+" m " + joins + query + paginatedQuery);
        return "SELECT m.* from "+modelClass.getSimpleName()+" m " + joins + query + paginatedQuery;
    }

    public String getCountPredicate(Class modelClass){

        return getPredicate(modelClass).replace("m.*","count(*) as totalelements").replace(" LIMIT " + pagination.getSize() + " OFFSET " + (pagination.getPage() * pagination.getSize()),"");
    }

    private String mapAttributeToQuery(String name, Object value){

        if(value instanceof String)
            return name+ " like '%" + value + "%' " ;
        if(value instanceof Long || value instanceof Integer || value instanceof Double || value instanceof Float) {
            return name+ " = " + value + " " ;
        }

        return "";
    }


}
