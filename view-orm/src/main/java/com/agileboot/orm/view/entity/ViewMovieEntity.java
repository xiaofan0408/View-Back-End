package com.agileboot.orm.view.entity;

import com.agileboot.common.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaofan0408
 * @since 2023-03-19
 */
@Getter
@Setter
@TableName("view_movie")
@ApiModel(value = "ViewMovieEntity对象", description = "")
public class ViewMovieEntity extends BaseEntity<ViewMovieEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("識別碼")
    @TableField("censored_id")
    private String censoredId;

    @TableField("avmoo_code_36")
    private String avmooCode36;

    @ApiModelProperty("https://pics.javbus.info/cover/5trd_b.jpg")
    @TableField("movie_pic_cover")
    private String moviePicCover;

    @TableField("movie_title")
    private String movieTitle;

    @ApiModelProperty("發行日期")
    @TableField("release_date")
    private String releaseDate;

    @TableField("gid")
    private String gid;

    @ApiModelProperty("長度分鐘")
    @TableField("movie_length")
    private String movieLength;

    @ApiModelProperty("導演")
    @TableField("Director")
    private String director;

    @ApiModelProperty("製作商")
    @TableField("Studio")
    private String studio;

    @ApiModelProperty("發行商")
    @TableField("Label")
    private String label;

    @ApiModelProperty("系列")
    @TableField("Series")
    private String series;

    @ApiModelProperty("類別")
    @TableField("Genre")
    private String genre;

    @ApiModelProperty("演員")
    @TableField("JAV_Idols")
    private String javIdols;

    @ApiModelProperty("Similar Videos")
    @TableField("Similar")
    private String similar;

    @TableField("have_hd")
    private Integer haveHd;

    @TableField("have_sub")
    private Integer haveSub;

    @TableField("have_magnet")
    private Integer haveMagnet;

    @TableField("Label_code")
    private String labelCode;

    @TableField("Series_code")
    private String seriesCode;

    @ApiModelProperty("https://pics.dmm.co.jp")
    @TableField("sample_dmm")
    private String sampleDmm;

    @TableField("magnet_date")
    private Date magnetDate;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
