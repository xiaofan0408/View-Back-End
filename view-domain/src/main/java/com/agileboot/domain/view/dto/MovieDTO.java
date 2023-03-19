package com.agileboot.domain.view.dto;

import com.agileboot.orm.view.entity.ViewMovieEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MovieDTO", description = "电影DTO")
public class MovieDTO {

        public MovieDTO(ViewMovieEntity entity){

            id = entity.getId();
            title = entity.getMovieTitle();
            movieId = entity.getCensoredId();
            coverUrl = entity.getMoviePicCover();
        }


        private Long id;

        private String title;

        private String movieId;

        private String coverUrl;

}
