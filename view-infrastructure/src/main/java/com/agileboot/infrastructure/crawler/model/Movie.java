package com.agileboot.infrastructure.crawler.model;

import lombok.Data;

import java.util.List;

@Data
public class Movie {

    private String date;

    private String title;

    private String id;

    private String img;

    private List<String> tags;
}
