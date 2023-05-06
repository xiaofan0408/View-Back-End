package com.agileboot.admin.controller.view;


import com.agileboot.common.core.dto.ResponseDTO;
import com.agileboot.domain.view.ViewMovieAppService;
import com.agileboot.domain.view.query.ListQuery;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@Tag(name = "磁力API", description = "磁力相关接口")
@RestController
@RequestMapping("/view/magnet")
public class ViewMagnetController extends BaseController {

    @Autowired
    private ViewMovieAppService viewMovieAppService;

    @Operation(summary = "列表", description = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieId", required = false)
    })
    @GetMapping("/list")
    public ResponseDTO<Map<String,Object>> page(@RequestParam("movieId") String movieId){
        Map<String,Object> data = viewMovieAppService.magnetList(movieId);
        return ResponseDTO.ok(data);
    }

}

