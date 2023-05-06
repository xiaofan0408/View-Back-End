package com.agileboot.infrastructure.crawler;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.common.core.page.Pagination;
import com.agileboot.infrastructure.crawler.constant.Constants;
import com.agileboot.infrastructure.crawler.model.*;
import com.agileboot.infrastructure.crawler.utils.RegUtils;
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
        Elements elements =  document.select("body > a");
        Map<String,Magnet> magnets = new HashMap<>();
        String prv = null;
        int index = 0;
        for (Element a : elements) {
            String link = a.attr("href");
            if (StringUtils.isBlank(link)) {
                magnets.get(prv).setHD(true);
                index = 0;
            } else {
                if (Objects.nonNull(prv)) {
                    if (!prv.equals(link)) {
                        prv = link;
                        index = 0;
                    }
                }
                if (!magnets.containsKey(link)) {
                    String id = RegUtils.regLink(link);
                    Magnet magnet = new Magnet();
                    magnet.setId(id);
                    magnet.setLink(link);
                    magnets.put(link,magnet);
                }
                if (index == 0) {
                    magnets.get(link).setTitle(a.text());
                } else if (index == 1) {
                    magnets.get(link).setShareDate(a.text());
                } else if (index == 2){
                    magnets.get(link).setNumberSize(a.text());
                }
                prv = link;
                index = index + 1;
            }
        }
        return new ArrayList<>(magnets.values());
    }

    public Map<String,Object> getMoviesByPage(String page, String magnet){
        Map<String,Object> resp = new HashMap<>();
        try {
            String prefix = Constants.JAVBUS;
            String url = page.equals("1") ? prefix: prefix +"/page/"+page;
            Map<String,String> cookies = getCookies(magnet);
            Document document = doGet(url,cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            resp.put("page",moviePageDTO);
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
            String url = prefix + "/" + keyword + "/" + page + "&type=1";
            Map<String,String> cookies = getCookies(magnet);
            Document document = doGet(url,cookies);
            PageDTO<Movie> moviePageDTO = parseMoviesPage(document);
            resp.put("page",moviePageDTO);
            return resp;
        } catch (Exception e){

        }
        return resp;
    }


    public List<Magnet> getMovieMagnets(String movieId,String gid,String uc){
        try {
            String searchParams = "?lang=zh&"+"gid="+gid + "&uc=" +uc;
            Map<String,Object> body = new HashMap<>();
            Map<String,String> headers = new HashMap<>();
            headers.put("referer",Constants.JAVBUS + "/" + movieId);
            Document document = doPost(body,headers,Constants.JAVBUS+"/ajax/uncledatoolsbyajax.php" + searchParams);
            return convertMagnetsHTML(document);
        }catch (Exception e){

        }
        return Collections.emptyList();
    }

    public List<Magnet> getMovieMagnetsByMovieId(String movieId){
        try {
            String url = Constants.JAVBUS + "/" + movieId;
            Document document = doGet(url,new HashMap<>());
            String gid = RegUtils.gid(document.html());
            String uc = RegUtils.uc(document.html());
            return getMovieMagnets(movieId,gid,uc);
        }catch (Exception e){

        }
        return null;
    }


    public MovieDetail getMovieDetail(String id){
        try {
            String url = Constants.JAVBUS + "/" + id;
            Document document = doGet(url,new HashMap<>());
            String title = document.select(".container h3").text();
            String img = formatImageUrl(document.select(".container .movie .bigImage img").attr("src"));
            ImageSize imageSize = new ImageSize();
            Elements infoNodes = document.select(".container .movie .info p");
            String date = textInfoFinder(infoNodes,"發行日期:");
            String videoLength = textInfoFinder(infoNodes, "長度:", "分鐘");
            Integer numberVideoLength = StringUtils.isNotBlank(videoLength) ? Integer.parseInt(videoLength) : null;
            MovieDetail.U director = linkInfoFinder(infoNodes, "導演:", "director");
            MovieDetail.U producer = linkInfoFinder(infoNodes, "製作商:", "studio");
            MovieDetail.U publisher = linkInfoFinder(infoNodes, "發行商:", "label");
            MovieDetail.U series = linkInfoFinder(infoNodes, "系列:", "series");

//  const genres = multipleInfoFinder<Property>(
//                    infoNodes,
//                    'genre',
//                    (genre) => !genre.hasAttribute('onmouseover'),
//                    (genre) => genre.querySelector('label a')
//  );
//  const stars = multipleInfoFinder<Property>(
//                    infoNodes,
//                    'star',
//                    (genre) => genre.hasAttribute('onmouseover'),
//                    (genre) => genre.querySelector('a')
//  );
//
//            /* ----------------- 磁力链接 ------------------ */
//  const gidReg = /var gid = (\d+);/;
//  const ucReg = /var uc = (\d+);/;
//
//  const gid = res.match(gidReg)?.[1] ?? null;
//  const uc = res.match(ucReg)?.[1] ?? null;
//
//  const magnets = gid && uc ? await getMovieMagnets({ movieId: id, gid, uc }) : [];
//
//            /* ----------------- 样品图片 ------------------ */
//  const samples = doc
//                    .querySelectorAll('#sample-waterfall .sample-box')
//                    .map<Sample>((box) => {
//      const img = box.querySelector('.photo-frame img');
//      const thumbnail = formatImageUrl(img?.getAttribute('src')) ?? '';
//      const filename = thumbnail.split('/').pop();
//      const id = filename?.match(/(\S+)\.(jpe?g|png|webp|gif)$/i)?.[1] ?? '';
//      const alt = img?.getAttribute('title') ?? null;
//      const src = formatImageUrl(box.getAttribute('href')) ?? null;
//            return {
//                    alt,
//                    id,
//                    src,
//                    thumbnail,
//            };
//    })
//    .filter(({ id, thumbnail }) => Boolean(id) && Boolean(thumbnail));
//
//            return {
//                    id,
//                    title,
//                    img,
//                    imageSize,
//                    date,
//                    videoLength: numberVideoLength,
//                    director,
//                    producer,
//                    publisher,
//                    series,
//                    genres,
//                    stars,
//                    magnets,
//                    samples,
//  };
        MovieDetail movieDetail = new MovieDetail();

        }catch (Exception e){

        }
        return null;
    }

    private MovieDetail.U linkInfoFinder(Elements infoNodes, String s, String director) {
        return null;
    }

    private String textInfoFinder(Elements infoNodes, String ...args) {
        return null;
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
                            .post();
        return document;
    }

   private Document doGet(String url, Map<String,String> cookies) throws IOException {
        Document document = Jsoup.connect(url)
                .cookies(cookies)
                .timeout(10000)
                .userAgent(Constants.USER_AGENT)
                .get();
        return document;
    }


    public static void main(String[] args) {
        JavBusCrawler javBusCrawler = new JavBusCrawler();
//        PageDTO<Movie> pageDTO = javBusCrawler.getMoviesByPage("1","exist");
//        pageDTO.getRows().forEach(movie -> {
//            System.out.println(movie);
//        });

//        Map<String,Object> resp = javBusCrawler.getMoviesByStarAndPage("2xi","1","exist");
//        System.out.println(JSONUtil.toJsonStr(resp));

//        Map<String,Object> resp = javBusCrawler.getMoviesByKeywordAndPage("三上","1","exist");
//        System.out.println(JSONUtil.toJsonStr(resp));

//        Map<String,Object> resp = javBusCrawler.getMoviesByTagAndPage("2t","1","exist");
//        System.out.println(JSONUtil.toJsonStr(resp));
        List<Magnet> magnets = javBusCrawler.getMovieMagnets("SSIS-406","1","exist");
        System.out.println(JSONUtil.toJsonStr(magnets));
    }
}