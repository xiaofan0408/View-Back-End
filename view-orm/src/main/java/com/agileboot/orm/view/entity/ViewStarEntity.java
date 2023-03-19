package com.agileboot.orm.view.entity;

import com.agileboot.common.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("view_star")
@ApiModel(value = "ViewStarEntity对象", description = "")
public class ViewStarEntity extends BaseEntity<ViewStarEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("code_36")
    private String code36;

    @TableField("star_name")
    private String starName;

    @TableField("star_birthday")
    private String starBirthday;

    @TableField("star_age")
    private Integer starAge;

    @TableField("star_cupsize")
    private String starCupsize;

    @TableField("star_height")
    private Integer starHeight;

    @TableField("star_bust")
    private Integer starBust;

    @TableField("star_waist")
    private Integer starWaist;

    @TableField("star_hip")
    private Integer starHip;

    @TableField("hometown")
    private String hometown;

    @ApiModelProperty("https://jp.netcdn.space/mono/actjpgs/")
    @TableField("hobby")
    private String hobby;

    @ApiModelProperty("https://jp.netcdn.space/mono/actjpgs/")
    @TableField("star_pic")
    private String starPic;

    @TableField("favorite")
    private Integer favorite;

    @TableField("file_num")
    private Integer fileNum;

    @TableField("code_10")
    private Integer code10;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
