package com.agileboot.domain.view.query;

import cn.hutool.core.util.StrUtil;
import com.agileboot.orm.common.query.AbstractPageQuery;
import com.agileboot.orm.view.entity.ViewMovieEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;


@Data
@NoArgsConstructor
@Schema(name = "电影参数")
public class PageQuery<T> {

    public static final int MAX_PAGE_NUM = 200;
    public static final int MAX_PAGE_SIZE = 500;

    @Max(MAX_PAGE_NUM)
    protected Integer pageNum = 1;
    @Max(MAX_PAGE_SIZE)
    protected Integer pageSize = 10;

    public Page<T> toPage() {
        return new Page<>(pageNum, pageSize);
    }
}
