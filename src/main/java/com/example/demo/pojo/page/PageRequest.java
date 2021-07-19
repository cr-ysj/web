package com.example.demo.pojo.page;

import lombok.Data;

import java.util.Map;

@Data
public class PageRequest {
    private int page;
    private int limit;

    private Map queryParams;
}
