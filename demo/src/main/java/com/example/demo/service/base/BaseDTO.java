package com.example.demo.service.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseDTO <Model>{

    @Null
    private Long id;
    @JsonIgnore
    private List<String> onlyRead = List.of("id");
}
