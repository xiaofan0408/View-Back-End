package com.agileboot.infrastructure.crawler;


import cn.hutool.json.JSONObject;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.core.page.Pagination;
import com.agileboot.infrastructure.crawler.constant.Constants;
import com.agileboot.infrastructure.crawler.model.Movie;
import com.agileboot.infrastructure.crawler.model.MovieTag;
import com.agileboot.infrastructure.crawler.model.StarInfo;
import org.apache.commons.lang3.StringUtils;
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
            Pagination pagination = parsePage(document);
            moviePage.setRows(movies);
            moviePage.setPagination(pagination);
        }catch (Exception e){

        }
        return moviePage;
    }

    private Pagination parsePage(Document document) {

         Long  currentPage = Long.parseLong(document.select(".pagination .active a").text());
         List<Long> pages = document.select(".pagination li a").stream()
                 .map(Element::text)
                 .filter(this::isDigit)
                 .map(Long::parseLong)
                 .collect(Collectors.toList());
         boolean hasNextPage = !document.select(".pagination li #next").isEmpty();
         Long nextPage = hasNextPage ? currentPage + 1 : null;
         return new Pagination(currentPage,hasNextPage,nextPage,pages);
    }

    private boolean isDigit(String str){
        for (char ch : str.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
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


    private StarInfo parseStarInfo(String url,String starId) {
        try {
            Document document = Jsoup.connect(Constants.JAVBUS + url)
                    .timeout(10000)
                    .userAgent(Constants.USER_AGENT)
                    .get();

            Elements elements = document.select("#waterfall .item .avatar-box");
            String avatar = formatImageUrl(elements.select(".photo-frame img").attr("src"));
            String name = elements.select(".photo-info .pb10").text();
            name = StringUtils.isBlank(name)?"":name;
            Elements infos = elements.select(".photo-info p");
            List<String> textList =  infos.stream().map(o->o.text()).collect(Collectors.toList());
            JSONObject jsonObject = new JSONObject();

            Constants.starInfoMap.forEach((key, mapValue)->{
                String value = textList.stream().filter(t -> t.indexOf(mapValue) >= 0).findFirst().orElse(null);
                if (StringUtils.isNotBlank(value)) {
                    value = value.replace(mapValue,"");
                }
                jsonObject.putOnce(key,value);
            });
            jsonObject.putOnce("avatar",avatar);
            jsonObject.putOnce("id",starId);
            jsonObject.putOnce("name",name);
            return jsonObject.toBean(StarInfo.class);
        }catch (Exception e){

        }
        return null;
    }

    private MovieTag parseTagInfo(Document document,String tagId){
        return null;
    }

    public static void main(String[] args) {
        JavBusCrawler javBusCrawler = new JavBusCrawler();
        PageDTO<Movie> pageDTO = javBusCrawler.parseMoviesPage("/","exist");
        pageDTO.getRows().forEach(movie -> {
            System.out.println(movie);
        });

        StarInfo starInfo = javBusCrawler.parseStarInfo("/star/2xi","2xi");
        System.out.println(starInfo);

    }

}
