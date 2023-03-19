package com.agileboot.admin.controller.view;


import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "磁力API", description = "磁力相关接口")
@RestController
@RequestMapping("/orm/view-magnet-entity")
public class ViewMagnetController extends BaseController {

}

