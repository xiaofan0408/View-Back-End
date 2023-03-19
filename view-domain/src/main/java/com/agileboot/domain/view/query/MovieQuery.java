package com.agileboot.domain.view.query;

import cn.hutool.core.util.StrUtil;
import com.agileboot.orm.common.query.AbstractPageQuery;
import com.agileboot.orm.view.entity.ViewMovieEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Schema(name = "电影参数")
public class MovieQuery  extends AbstractPageQuery<ViewMovieEntity> {

    private String movieId;

    private String queryName;

    @Override
    public QueryWrapper<ViewMovieEntity> toQueryWrapper() {
        QueryWrapper<ViewMovieEntity> queryWrapper = new QueryWrapper<ViewMovieEntity>()
                .like(StrUtil.isNotEmpty(queryName), "movie_title", queryName)
                .like(StrUtil.isNotEmpty(movieId), "censored_id", movieId);

        this.setOrderByColumn("release_date");
        this.setIsAsc("descending");
        addSortCondition(queryWrapper);
        return queryWrapper;
    }
}
