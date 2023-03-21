package com.agileboot.infrastructure.crawler.model;

import lombok.Data;

@Data
public class Magnet {

    private String id;

    private String link;

    private boolean isHD;

    private String title;

    private int numberSize;

    private String shareDate;

    private boolean hasSubtitle;

}
