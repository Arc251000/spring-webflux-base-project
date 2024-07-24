package com.example.demo.service.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public abstract class BaseModel {

    @Id
    Long id;
    @JsonIgnore
    Boolean deleted = false;

}
