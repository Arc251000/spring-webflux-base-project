package com.example.demo.service.security.model;

import com.example.demo.service.base.BaseModel;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
public class Role extends BaseModel {

    private String name;

}
