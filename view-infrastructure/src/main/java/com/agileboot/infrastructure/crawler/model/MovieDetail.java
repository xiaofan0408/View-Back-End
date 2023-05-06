package com.agileboot.infrastructure.crawler.model;

import lombok.Data;

import java.util.List;

@Data
public class MovieDetail {

    private String id;

    private String title;

    private String img;

    private String date;

    private int videoLength;

    private U director;

    private U producer;

    private U publisher;

    private U series;

    private List<MovieTag> tags;

    private List<MovieStar> stars;

    private List<Magnet> magnets;

    private ImageSize imageSize;

    private List<Sample> samples;

    @Data
    public static class U {

        private String id;

        private String name;
    }


}
