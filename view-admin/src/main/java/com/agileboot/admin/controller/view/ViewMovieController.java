package com.agileboot.admin.controller.view;



import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.common.core.page.PageDTO;
import com.agileboot.domain.view.ViewMovieAppService;
import com.agileboot.domain.view.dto.MovieDTO;
import com.agileboot.domain.view.query.ListQuery;
import com.agileboot.domain.view.query.MovieQuery;
import com.agileboot.domain.view.query.PageQuery;
import com.agileboot.domain.view.query.SearchQuery;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.agileboot.common.core.base.BaseController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaofan0408
 * @since 2023-03-19
 */
@Tag(name = "VIEW-API", description = "View相关接口")
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

//    /view/movie/page?page=1&pageSize=20&starId=xx&tagId=xx

    @Operation(summary = "列表", description = "分页获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",required = false),
            @ApiImplicitParam(name = "pageSize", required = false),
            @ApiImplicitParam(name = "starId", required = false),
            @ApiImplicitParam(name = "tagId", required = false)
    })
    @GetMapping("/page")
    public ResponseDTO<Map<String,Object>> page(@ApiIgnore ListQuery query){
        Map<String,Object> data = viewMovieAppService.getPage(query);
        return ResponseDTO.ok(data);
    }

    @Operation(summary = "搜索", description = "搜索")
    @GetMapping("/search")
    public ResponseDTO<Map<String,Object>> page(SearchQuery query){
        Map<String,Object> data = viewMovieAppService.search(query);
        return ResponseDTO.ok(data);
    }

}

