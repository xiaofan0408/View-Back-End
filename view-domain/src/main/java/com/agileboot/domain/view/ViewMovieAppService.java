package com.agileboot.domain.view;


import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.view.dto.MovieDTO;
import com.agileboot.domain.view.query.MovieQuery;
import com.agileboot.orm.view.entity.ViewMovieEntity;
import com.agileboot.orm.view.service.IViewMovieService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewMovieAppService {

    @NonNull
    private IViewMovieService iViewMovieService;


    public PageDTO<MovieDTO> getMovieList(MovieQuery query) {
        Page<ViewMovieEntity> page = iViewMovieService.page(query.toPage(), query.toQueryWrapper());
        List<MovieDTO> records = page.getRecords().stream().map(MovieDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
