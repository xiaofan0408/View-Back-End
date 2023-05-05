package com.agileboot.infrastructure.crawler;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.core.page.Pagination;
import com.agileboot.infrastructure.crawler.constant.Constants;
import com.agileboot.infrastructure.crawler.model.Magnet;
import com.agileboot.infrastructure.crawler.model.Movie;
import com.agileboot.infrastructure.crawler.model.MovieTag;
import com.agileboot.infrastructure.crawler.model.StarInfo;
import com.agileboot.infrastructure.crawler.utils.EncodingUtil;
import com.agileboot.infrastructure.crawler.utils.RegUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JavBusCrawler {


    public PageDTO<Movie> parseMoviesPage(Document document){
        PageDTO<Movie> moviePage = new PageDTO<Movie>(Collections.emptyList());
        List<Movie> movies = new ArrayList<>();

        Elements elements = document.select("#waterfall #waterfall .item");
        for (Element element : elements) {
            Movie movie = parseMovie(element);
            movies.add(movie);
        }
        Pagination pagination = parsePage(document);
        moviePage.setRows(movies);
        moviePage.setPagination(pagination);
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


    private StarInfo parseStarInfo(Document document,String starId) {
        try {
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
        Elements elements = document.select("title");
        String tag = RegUtils.regTag(elements.text());
        return new MovieTag(tagId,tag);
    }


    public List<Magnet> convertMagnetsHTML(Document document){
        Elements elements =  document.select("tr");
        List<Magnet> magnets = new ArrayList<>();
        for (Element tr : elements) {
            String link = Optional.ofNullable(tr.select("td a").attr("href")).orElse("");
            String id = RegUtils.regLink(link);
            boolean isHD = tr.select("td").select("a").text().indexOf("高清")>0;
            boolean hasSubtitle = tr.select("td").select("a").text().indexOf("字幕")>0;
            String title = tr.select("td a").text().trim();
            String size = tr.select("td:nth-child(2) a").text().trim();
            Integer numberSize = StringUtils.isNotBlank(size) ? Integer.parseInt(size) : 0;
            String shareDate = tr.select("td:nth-child(3) a").text().trim();
            Magnet magnet = new Magnet(id,link,isHD,title,numberSize,shareDate,hasSubtitle);
            magnets.add(magnet);
        }
        return magnets;
    }

    public Map<String,Object> getMoviesByPage(String page, String magnet){
        Map<String, Object> resp = new HashMap<>();
        try {
            String prefix = Constants.JAVBUS;
            String url = page.equals("1") ? prefix: prefix +"/page/"+page;
            Map<String,String> cookies = getCookies(magnet);
            Document document = doGet(url,cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            resp.put("page", moviePageDTO);
        } catch (Exception e){

        }
        return resp;
    }

    public Map<String,Object> getMoviesByStarAndPage(String starId,String page, String magnet) {
        Map<String, Object> resp = new HashMap<>();
        try {
            String prefix = Constants.JAVBUS + "/star";
            String url = page.equals("1") ? prefix + "/" + starId : prefix + "/" + starId + "/" + page;
            Map<String, String> cookies = getCookies(magnet);
            Document document = doGet(url, cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            StarInfo starInfo = parseStarInfo(document, starId);
            resp.put("starInfo", starInfo);
            resp.put("page", moviePageDTO);
            return resp;
        } catch (Exception e) {

        }
        return resp;
    }


    public Map<String,Object> getMoviesByTagAndPage(String tagId,String page, String magnet){
        Map<String,Object> resp = new HashMap<>();
        try {
            String prefix = Constants.JAVBUS + "/genre";
            String url = page.equals("1") ? prefix + "/" + tagId: prefix + "/" + tagId + "/" + page;
            Map<String,String> cookies = getCookies(magnet);
            Document document = doGet(url,cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            MovieTag movieTag = parseTagInfo(document,tagId);
            resp.put("movieTag",movieTag);
            resp.put("page",moviePageDTO);
            return resp;
        } catch (Exception e){

        }
        return resp;
    }


    public Map<String,Object> getMoviesByKeywordAndPage(String keyword,String page, String magnet){
        Map<String,Object> resp = new HashMap<>();
        try {
            String prefix = Constants.JAVBUS + "/search";
            String url = prefix + "/" + EncodingUtil.encodeURIComponent(keyword) + "/" + page + "&type=1";
            Map<String,String> cookies = getCookies(magnet);
            Document document = doGet(url,cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            resp.put("page",moviePageDTO);
            return resp;
        } catch (Exception e){
            log.warn("error: ",e);
        }
        return resp;
    }


    private List<Magnet> getMovieMagnets(String movieId,String gid,String uc){
        try {
            Map<String,Object> body = new HashMap<>();
            body.put("lang","zh");
            body.put("gid",gid);
            body.put("uc",uc);
            Map<String,String> headers = new HashMap<>();
            headers.put("referer",Constants.JAVBUS + "/" + movieId);
            Document document = doPost(body,headers,Constants.JAVBUS+"/ajax/uncledatoolsbyajax.php");
            return convertMagnetsHTML(document);
        }catch (Exception e){

        }
        return Collections.emptyList();
    }


    private Map<String,String> getCookies(String magnet){
        Map<String,String> cookies = new HashMap<>();
        if (Constants.MagnetType_e.equals(magnet)) {
            cookies.put("existmag","mag");
        } else {
            cookies.put("existmag","all");
        }
        return cookies;
    }

    private Document doPost(Map<String,Object> body,Map<String,String> headers,String url) throws IOException {
        Document document = Jsoup.connect(url)
                            .headers(headers)
                            .timeout(10000)
                            .userAgent(Constants.USER_AGENT)
                            .requestBody(JSONUtil.toJsonStr(body))
                            .post(