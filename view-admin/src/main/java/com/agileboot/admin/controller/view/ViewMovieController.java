package com.agileboot.admin.controller.view;



import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.view.ViewMovieAppService;
import com.agileboot.domain.view.dto.MovieDTO;
import com.agileboot.domain.view.query.MovieQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.agileboot.common.core.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaofan0408
 * @since 2023-03-19
 */
@Tag(name = "电影API", description = "电影相关接口")
@RestController
@RequestMapping("/view/movie")
@RequiredArgsConstructor
public class ViewMovieController extends BaseController {

    @NonNull
    private ViewMovieAppService viewMovieAppService;

    @Operation(summary = "获取列表", description = "分页获取列表")
    @GetMapping("/list")
    public ResponseDTO<PageDTO<MovieDTO>> list(MovieQuery query) {
        PageDTO<MovieDTO> page = viewMovieAppService.getMovieList(query);
        return ResponseDTO.ok(page);
    }


}

