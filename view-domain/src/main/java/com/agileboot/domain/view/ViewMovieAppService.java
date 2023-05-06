package com.agileboot.domain.view;


import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.view.dto.MovieDTO;
import com.agileboot.domain.view.query.ListQuery;
import com.agileboot.domain.view.query.MovieQuery;
import com.agileboot.domain.view.query.SearchQuery;
import com.agileboot.infrastructure.crawler.JavBusCrawler;
import com.agileboot.infrastructure.crawler.model.Magnet;
import com.agileboot.orm.view.entity.ViewMovieEntity;
import com.agileboot.orm.view.service.IViewMovieService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewMovieAppService {

    @NonNull
    private IViewMovieService iViewMovieService;

    @NonNull
    private JavBusCrawler crawler;


    public PageDTO<MovieDTO> getMovieList(MovieQuery query) {
        Page<ViewMovieEntity> page = iViewMovieService.page(query.toPage(), query.toQueryWrapper());
        List<MovieDTO> records = page.getRecords().stream().map(MovieDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public Map<String, Object> getPage(ListQuery query) {
        if (StringUtils.isNotBlank(query.getStarId())) {
            return crawler.getMoviesByStarAndPage(query.getStarId(),query.getPageNum().toString(),"exist");
        } else if (StringUtils.isNotBlank(query.getTagId())){
            return crawler.getMoviesByTagAndPage(query.getTagId(),query.getPageNum().toString(),"exist");
        }
        return crawler.getMoviesByPage(query.getPageNum().toString(),"exist");
    }

    public Map<String, Object> search(SearchQuery query) {
        if (StringUtils.isNotBlank(query.getKeyword())) {
            return crawler.getMoviesByKeywordAndPage(query.getKeyword(),query.getPageNum().toString(),"exist");
        }
        return new HashMap<>();
    }

    public Map<String, Object> magnetList(String movieId) {
        List<Magnet> magnetList = crawler.getMovieMagnetsByMovieId(movieId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",magnetList);
        return map;
    }
}
