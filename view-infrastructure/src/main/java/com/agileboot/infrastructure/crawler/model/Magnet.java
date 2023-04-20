package com.agileboot.infrastructure.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Magnet {

    private String id;

    private String link;

    private boolean isHD;

    private String title;

    private int numberSize;

    private String shareDate;

    private boolean hasSubtitle;

}
