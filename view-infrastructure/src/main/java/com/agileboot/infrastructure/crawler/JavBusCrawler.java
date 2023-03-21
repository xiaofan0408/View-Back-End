package com.agileboot.infrastructure.crawler;


import com.agileboot.common.core.page.PageDTO;
import com.agileboot.infrastructure.crawler.constant.Constants;
import com.agileboot.infrastructure.crawler.model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JavBusCrawler {




    public PageDTO<Movie> parseMoviesPage(String url,String magnet){

        PageDTO<Movie> moviePage = new PageDTO<Movie>(Collections.emptyList());
        try {
            Map<String,String> cookies = new HashMap<>();
            if (Constants.MagnetType_e.equals(magnet)) {
                cookies.put("existmag","mag");
            } else {
                cookies.put("existmag","all");
            }

            List<Movie> movies = new ArrayList<>();
            Document document = Jsoup.connect(Constants.JAVBUS + url)
                    .cookies(cookies)
                    .timeout(10000)
                    .userAgent(Constants.USER_AGENT)
                    .get();

            Elements elements = document.select("#waterfall #waterfall .item");
            for (Element element : elements) {
                Movie movie = parseMovie(element);
                movies.add(movie);
            }
            moviePage.setRows(movies);
        }catch (Exception e){

        }
        return moviePage;
    }

    private Movie parseMovie(Element element) {
        String img = formatImageUrl(element.select(".photo-frame img").attr("src"));
        String title = element.select(".photo-frame img").attr("title");
        Elements info = element.select(".photo-info date");
        String id = info.get(0).text();
        String date = info.get(1).text();
        List<String> tags = element.select(".item-tag button").stream().map(e->e.text()).collect(Collectors.toList());
        Movie movie = new Movie();
        movie.setImg(img);
        movie.setTitle(title);
        movie.setId(id);
        movie.setDate(date);
        movie.setTags(tags);
        return movie;
    }

    private String formatImageUrl(String url){
        return url;
    }

    public static void main(String[] args) {
        JavBusCrawler javBusCrawler = new JavBusCrawler();
        PageDTO<Movie> pageDTO = javBusCrawler.parseMoviesPage("/","exist");
        pageDTO.getRows().forEach(movie -> {
            System.out.println(movie);
        });
    }

}
