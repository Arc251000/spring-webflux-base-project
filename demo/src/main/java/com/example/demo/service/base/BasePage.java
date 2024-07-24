package com.example.demo.service.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasePage {

    private List data;
    private long totalElements;
    private int page;
    private int size;

}
